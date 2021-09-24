package com.intermedia.printcoverage

import org.gradle.api.logging.Logger

interface PrinterFactory {
    fun create(logger: Logger): Printer
}
