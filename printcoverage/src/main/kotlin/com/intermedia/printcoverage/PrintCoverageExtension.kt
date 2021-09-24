package com.intermedia.printcoverage

import java.io.File

open class PrintCoverageExtension {
    lateinit var printerFactory: PrinterFactory
    lateinit var jacocoReportFile: File
}
