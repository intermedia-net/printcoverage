package com.intermedia.printcoverage.log

import com.intermedia.printcoverage.Printer
import org.gradle.api.logging.Logger

class LogPrinter(
    private val logger: Logger
) : Printer {

    override fun print(coverage: Double) {
        logger.info("Code coverage is: $coverage")
    }
}
