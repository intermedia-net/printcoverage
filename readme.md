# PrintCoverage

PrintCoverage plugin allows you easily integrate coverage badge into your Azure DevOps
pull requests!

[![Build Status][build-image]][build-url]
[![License: MIT][license-image]][license-url]

## How to use

In the module `build.gradle`:
```groovy
plugins {
    id('com.intermedia.printcoverage').version('0.0.1')
}

import com.intermedia.printcoverage.azure.AzurePrinterFactory
import com.intermedia.printcoverage.azure.AzureRepo

printCoverage {
    setPrinterFactory(
            new AzurePrinterFactory(
                    new AzureRepo(
                            "<azure token>",
                            "<azure base url>",
                            "<azure organization>" ,
                            "<azure project>",
                            "<azure repo>"
                    )
            )
    )
    setJacocoReportFile(file("${project.buildDir}/outputs/jacoco/report.xml"))
}
```

Where azure token is a personal access token with custom scopes:
- Code (Status) 
- Code (Read & Write)

And then run it in the Azure pipeline as a policy for a target branch:
```shell
./gradlew testDebugUnitTest printCoverage
```

## How it works
The plugin processes the jacoco report xml file, analyzes the test coverage stat, and then posts it via Azure
DevOps api as a pull request status update.

<!-- Badges -->

[build-image]: https://github.com/intermedia-net/printcoverage/actions/workflows/ci.yml/badge.svg
[build-url]: https://github.com/intermedia-net/printcoverage/actions/workflows/ci.yml
[license-image]: https://img.shields.io/badge/License-MIT-yellow.svg
[license-url]: https://github.com/intermedia-net/printcoverage/blob/main/LICENSE
