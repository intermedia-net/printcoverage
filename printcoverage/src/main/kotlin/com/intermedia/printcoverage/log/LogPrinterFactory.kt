package com.intermedia.printcoverage.log

import com.intermedia.printcoverage.Printer
import com.intermedia.printcoverage.PrinterFactory
import org.gradle.api.logging.Logger

class LogPrinterFactory : PrinterFactory {
    override fun create(logger: Logger): Printer {
        return LogPrinter(logger)
    }
}
