# pitest

PIT mutation testing around a small order pricer, with a mutation-score gate and a profile that runs only a weak test.

## Goal

Fail the build when the mutation score drops below a threshold (90 here), because executing a line is not the same as asserting its behaviour.

## Run it

Needs Java 21 and Maven (network on first run).

```
mvn -B test-compile org.pitest:pitest-maven:mutationCoverage
mvn -B -Pweak test-compile org.pitest:pitest-maven:mutationCoverage
```

Expected: the first run ends with `Generated 10 mutations Killed 10 (100%)` and `BUILD SUCCESS`. The second ends with `Generated 10 mutations Killed 4 (40%)` and `BUILD FAILURE: Mutation score of 40 is below threshold of 90`.

## What it proves

- `mutationThreshold` in `pom.xml` makes PIT exit non-zero; the `weak` profile excludes `OrderPricerTest` and leaves only `CoverageOnlyTest`, which scores 40%.
- `CoverageOnlyTest` calls the same code paths but asserts almost nothing, so the mutants in `OrderPricer.java` survive; PIT reports the same line coverage (4/5, the private constructor is the unreached line) in both runs.
- The boundary test `discountAtBoundary` (quantity equal to `BULK_QTY`) is what kills the `>=` to `>` mutant.

## Trade-offs

- Slow: each mutant reruns the covering tests. Scope with `targetClasses` (set to `lab.*` here) and use incremental history in CI.
- Equivalent mutants can never be killed, so 100% is not a sensible target; 80-90% is.
- PIT is invoked by goal name and not bound to `verify`, so plain `mvn test` stays fast.

## When not to use it

- For code with few meaningful branches such as DTOs and glue.
- On slow, integration-heavy suites, or on very large modules without scoping to changed classes.
