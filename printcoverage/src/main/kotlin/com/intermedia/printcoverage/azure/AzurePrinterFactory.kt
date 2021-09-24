package com.intermedia.printcoverage.azure

import com.intermedia.printcoverage.Printer
import com.intermedia.printcoverage.PrinterFactory
import okhttp3.OkHttpClient
import org.gradle.api.logging.Logger

class AzurePrinterFactory(
    private val repo: AzureRepo
) : PrinterFactory {

    private val okHttpClient = OkHttpClient()

    override fun create(logger: Logger): Printer {
        return AzurePrinter(
            repo,
            System.getenv(ENV_PULL_REQUEST_ID_NAME) ?: "",
            System.getenv(ENV_BUILD_ID_NAME) ?: "",
            okHttpClient,
            logger
        )
    }

    companion object {
        private const val ENV_PULL_REQUEST_ID_NAME = "SYSTEM_PULLREQUEST_PULLREQUESTID"
        private const val ENV_BUILD_ID_NAME = "BUILD_BUILDID"
    }
}
