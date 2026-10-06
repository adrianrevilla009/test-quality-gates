# archunit

ArchUnit rules for web, service and repo layering and package cycles, checked against a good package (`lab.orders`) and a deliberately broken one (`lab.bad`).

## Goal

Turn architecture decisions into tests that fail the build when violated, and show that the rules really do catch a violation.

## Run it

Needs Java 21 and Maven (network on first run).

```
mvn -B test
```

Expected: `Tests run: 3, Failures: 0, Errors: 0, Skipped: 0` for `ArchitectureTest` and `BUILD SUCCESS`.

## What it proves

- `ordersRespectLayers` and `ordersHaveNoCycles` pass for `lab.orders`: web uses service, service uses repo, nothing calls upward, and slices are cycle free.
- `rulesFailOnTheBadSample` runs the same rules against `lab.bad`, where `BadRepository` imports `BadController` and the controller imports the repository, and expects an `AssertionError` for both the layer rule and the cycle rule.
- `withOptionalLayers(true)` in `ArchitectureTest.java` lets the layer rule run on `lab.bad`, which has no service package, so the failure comes from the real violation and not from an empty layer.

## Trade-offs

- Rules are code written with string package names, which are not compiler-checked; renaming packages means updating the rules.
- The `lab.bad` sample lives in `src/main` for simplicity; in a real project keep such fixtures in test sources.
- Importing a large classpath is slow; scope the imported packages.

## When not to use it

- In a codebase with one or two packages, where there is little structure to protect.
- When separate Maven modules or JPMS already enforce the boundaries at compile time.
