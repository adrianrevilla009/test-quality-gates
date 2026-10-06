# static-analysis-spotbugs-errorprone-checkstyle

Error Prone, Checkstyle and SpotBugs wired into one Maven build, with sample files that make each gate fail.

## Goal

Run three static analysers as build gates, Error Prone at compile time, Checkstyle and SpotBugs in `verify`, and show each of them failing on bad code.

## Run it

Needs Java 21 and Maven (network on first run).

```
mvn -B clean verify
mvn -B clean verify -Pbad-lint
mvn -B clean compile spotbugs:check -Pbad-lint
mvn -B clean verify -Pbad-errorprone
```

Expected: the first passes with `BUILD SUCCESS`. The second fails in Checkstyle with `There are 2 errors reported by Checkstyle`. The third fails in SpotBugs with `ES_COMPARING_PARAMETER_STRING_WITH_EQ`. The fourth fails to compile with `[EqualsIncompatibleType] Calling equals on incompatible types String and Integer`.

## What it proves

- Each tool catches a different class of defect: Error Prone works on the AST while compiling, Checkstyle on source style, SpotBugs on bytecode.
- `samples/lint/BadLint.java` and `samples/errorprone/BadErrorProne.java` are added only by the `bad-*` profiles, so the default build over `OrderLabel.java` stays green.
- Checkstyle runs before SpotBugs in `verify`, so in `bad-lint` it stops the build first; the separate `spotbugs:check` command shows the SpotBugs finding.

## Trade-offs

- Error Prone on JDK 21 needs javac internals exported; the compiler is forked with `-J--add-exports` and `--add-opens` flags in `pom.xml`, which slows compilation a little.
- SpotBugs `threshold` is `Medium`; lowering it brings more false positives.
- `checkstyle.xml` is a small ruleset on purpose; adopting a full standard on existing code produces a wall of violations.
- Gates are bound to `verify`, not `test`, to keep the inner loop fast.

## When not to use it

- Do not enable every check at once on a legacy codebase; start with a baseline or per-check warnings.
- Skip Error Prone if the build must run on a JDK or compiler it does not support.
