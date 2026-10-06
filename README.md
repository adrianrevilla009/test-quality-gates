# test-quality-gates

Five small Maven projects that turn test and code quality into build gates (mutation score, architecture rules, coverage, static analysis, flaky-test quarantine) and show each gate failing on purpose, using a tiny Orders domain.

## What is inside

| Folder | What it shows | Run |
| --- | --- | --- |
| [`pitest`](./pitest) | PIT mutation testing with a 90% threshold; a coverage-only test scores 40% and fails the gate | `mvn -B test-compile org.pitest:pitest-maven:mutationCoverage` |
| [`archunit`](./archunit) | ArchUnit layer and cycle rules for web, service and repo packages, checked against a good and a deliberately bad package | `mvn -B test` |
| [`jacoco-allure`](./jacoco-allure) | JaCoCo check requiring 80% line and branch coverage, plus an Allure report from the same run | `mvn -B clean verify allure:report` |
| [`static-analysis-spotbugs-errorprone-checkstyle`](./static-analysis-spotbugs-errorprone-checkstyle) | Error Prone, Checkstyle and SpotBugs as gates, with sample files that make each one fail | `mvn -B clean verify` |
| [`test-flakiness-quarantine`](./test-flakiness-quarantine) | A `@Quarantine` tag that removes tests from the blocking build, with a non-blocking retry job | `mvn -B clean test` |

## Prerequisites

- Java 21
- Maven 3.9 or newer
- Network access on the first run to download plugins (the Allure command also downloads the Allure CLI)

## How to read it

Start with `pitest`, which shows why a coverage number alone is not enough, then `jacoco-allure` for the coverage gate. The other three are independent. Each folder is a standalone Maven project with its own `pom.xml`; run the commands from inside the folder.
