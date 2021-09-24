package com.intermedia.printcoverage.azure

import java.util.Base64
import java.util.Locale

class AzureRepo(
    private val apiToken: String,
    private val baseUrl: String,
    private val organization: String,
    private val project: String,
    private val repoId: String
) {

    fun statusUrl(pullRequestId: String): String {
        return String.format(
            Locale.US,
            URL_TEMPLATE,
            baseUrl,
            organization,
            project,
            repoId,
            pullRequestId
        )
    }

    fun targetUrl(buildId: String): String {
        return String.format(
            Locale.US, TARGET_URL_TEMPLATE,
            baseUrl,
            organization,
            project,
            buildId
        )
    }

    fun authToken(): String {
        return Base64.getEncoder().encodeToString(":$apiToken".toByteArray())
    }

    companion object {
        const val URL_TEMPLATE = "%s/%s/%s/_apis/git/repositories/%s/pullRequests/%s/statuses?api-version=6.0-preview.1"
        const val TARGET_URL_TEMPLATE = "%s/%s/%s/_build/results?buildId=%s&view=codecoverage-tab"
    }
}
