package com.intermedia.printcoverage.azure

import com.intermedia.printcoverage.Printer
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.gradle.api.logging.Logger
import java.util.Locale

/**
 * Azure DevOps status query doc: https://docs.microsoft.com/en-us/rest/api/azure/devops/git/pull-request-statuses/pull-request-statuses-create?view=azure-devops-rest-6.0
 */
class AzurePrinter(
    private val repo: AzureRepo,
    private val pullRequestId: String,
    private val buildId: String,
    private val okHttpClient: OkHttpClient,
    private val logger: Logger
) : Printer {

    override fun print(coverage: Double) {
        val statusUrl = repo.statusUrl(pullRequestId)
        val targetUrl = repo.targetUrl(buildId)

        val description = "Coverage ${String.format(Locale.US, "%.2f", coverage)}%"
        val body = String.format(
            Locale.US,
            REQUEST_BODY,
            description,
            targetUrl
        ).toRequestBody(JSON_MEDIA_TYPE)
        val request = Request.Builder()
            .url(statusUrl)
            .post(body)
            .header(AUTH_HEADER, "Basic " + repo.authToken())
            .build()
        okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                logger.warn(
                    "Could not execute a call to azure devops API, " +
                        "origin url: '$statusUrl'," +
                        "response code: ${response.code}"
                )
            }
        }
    }

    companion object {
        private const val AUTH_HEADER = "Authorization"
        private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
        private const val REQUEST_BODY = "{" +
            "  \"state\": \"succeeded\"," +
            "  \"description\": \"%s\"," +
            "  \"context\": {" +
            "    \"name\": \"jacoco-coverage\"," +
            "    \"genre\": \"jacoco-coverage\"" +
            "  }," +
            "  \"targetUrl\": \"%s\"" +
            "}"
    }
}
