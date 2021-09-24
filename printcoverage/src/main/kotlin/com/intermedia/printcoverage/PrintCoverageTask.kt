package com.intermedia.printcoverage

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

open class PrintCoverageTask : DefaultTask() {

    private val db: DocumentBuilder by lazy {
        val documentBuilderFactory = DocumentBuilderFactory.newInstance()
        documentBuilderFactory.setAttribute(
            "http://apache.org/xml/features/nonvalidating/load-external-dtd",
            false
        )
        return@lazy documentBuilderFactory
            .newDocumentBuilder()
    }
    private val xPath: XPath by lazy {
        return@lazy XPathFactory.newInstance().newXPath()
    }

    @Input
    lateinit var printer: Printer

    @InputFile
    lateinit var jacocoReportFile: File

    @TaskAction
    fun printCoverage() {
        val reportDoc = db.parse(jacocoReportFile)
        val counters = xPath.compile(COUNTER_XPATH)
        val counterNodes = counters.evaluate(
            reportDoc,
            XPathConstants.NODESET
        ) as NodeList

        var missed = 0
        var covered = 0
        for (i in 0 until counterNodes.length) {
            val counterNode = counterNodes.item(i)
            missed += counterNode.attributes
                .getNamedItem(COUNTER_MISSED_ATTR)
                .textContent
                .toInt()
            covered += counterNode.attributes
                .getNamedItem(COUNTER_COVERED_ATTR)
                .textContent
                .toInt()
        }

        val coverage = COVERAGE_FULL / (missed + covered) * covered
        printer.print(coverage)
    }

    companion object {
        private const val COUNTER_XPATH = "//counter[@type='INSTRUCTION']"
        private const val COUNTER_MISSED_ATTR = "missed"
        private const val COUNTER_COVERED_ATTR = "covered"
        private const val COVERAGE_FULL = 100.0
    }
}
