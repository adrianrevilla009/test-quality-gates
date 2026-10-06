# test-flakiness-quarantine

A `@Quarantine` annotation, two quarantined tests, a policy test, and Maven profiles that run the quarantine as a non-blocking job.

## Goal

Keep flaky or known-broken tests from blocking merges without deleting them: tag them, run them in a separate job, and require an issue id on every quarantine.

## Run it

Needs Java 21 and Maven (network on first run).

```
mvn -B clean test
mvn -B clean test -Pquarantine
mvn -B clean test -Pretry-all
```

Expected: the first run executes 2 tests (`stableSum` and the policy test) and passes. The second runs only the quarantined tests with 2 retries, reports `Tests run: 2, Failures: 1, Flakes: 1` and still ends in `BUILD SUCCESS`. The third runs everything with retries, reports `Flakes: 1`, and ends in `BUILD FAILURE` because of `brokenCurrencyRounding`.

## What it proves

- `Quarantine.java` is a composed `@Tag("quarantine")` with a mandatory `issue`; surefire `excludedGroups` keeps those tests out of the default build.
- In `OrderTotalsTest.java`, `flakyAuditTimestamp` fails on its first attempt and passes on retry, so it is listed as a flake, while `brokenCurrencyRounding` always fails. The `quarantine` profile sets `testFailureIgnore`, so the build stays green.
- `QuarantinePolicyTest` fails the blocking build if a quarantined test has a blank issue.

## Trade-offs

- Retries hide flakiness if nobody reads the `Flakes` line; the nightly job needs an owner and an age limit for quarantines.
- Quarantined tests lose their protection, so keep the list short.
- The policy test lists test classes by hand; a classpath scan would remove that but add code.
- The flake is simulated with a per-JVM attempt counter, which keeps the lab deterministic but is not a real race.

## When not to use it

- Do not quarantine a test that is merely slow; fix or speed it up.
- Do not retry-and-ignore in the blocking build: a flaky test usually points at a real race or shared state.
