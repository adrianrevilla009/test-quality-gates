# jacoco-allure

JaCoCo coverage gate and an Allure report around a small shipping-cost calculator.

## Goal

Enforce a coverage threshold (80% lines and branches) with JaCoCo and produce a readable Allure test report from the same test run.

## Run it

Needs Java 21 and Maven (network on first run; the Allure plugin downloads its CLI).

```
mvn -B clean verify allure:report
mvn -B clean verify -Dtest='ShippingTest#light'
```

Expected: the first command ends with `BUILD SUCCESS` and writes the report to `target/site/allure-maven-plugin`. The second runs one test and fails with `lines covered ratio is 0.42, but expected minimum is 0.80` and `branches covered ratio is 0.33`.

## What it proves

- `jacoco:check` is bound to `verify` and fails the build when `Shipping.java` is covered below `coverage.min` (0.80), a property in `pom.xml`.
- The JaCoCo HTML report lands in `target/site/jacoco` and Allure raw results in `target/allure-results`.
- JaCoCo and Allure share one JVM: the surefire `argLine` starts with `@{argLine}` so the JaCoCo agent stays attached next to the AspectJ weaver that Allure needs for `@Description` and `@Feature`.

## Trade-offs

- Coverage says code ran, not that it was asserted; pair it with mutation testing (see `pitest`).
- Always use `clean` when repeating a failing scenario, because JaCoCo appends to `target/jacoco.exec` and stale data can hide the failure.
- `allure:report` is not bound to the build because it downloads a CLI; CI can publish `target/allure-results` instead.
- Allure's `.allure` download folder appears inside this folder after the report runs.

## When not to use it

- Do not chase a coverage number on generated code or glue.
- Skip Allure if the team already reads the test summary of its CI system.
