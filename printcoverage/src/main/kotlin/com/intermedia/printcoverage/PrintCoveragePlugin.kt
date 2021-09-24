package com.intermedia.printcoverage

import org.gradle.api.Plugin
import org.gradle.api.Project

class PrintCoveragePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create(
            EXTENSION_NAME,
            PrintCoverageExtension::class.java
        )
        project.afterEvaluate {
            project.tasks.register(TASK_NAME, PrintCoverageTask::class.java) { task ->
                val args = project.extensions.getByType(PrintCoverageExtension::class.java)
                task.printer = args.printerFactory.create(project.logger)
                task.jacocoReportFile = args.jacocoReportFile
            }
        }
    }

    companion object {
        private const val EXTENSION_NAME = "printCoverage"
        private const val TASK_NAME = "printCoverage"
    }
}
