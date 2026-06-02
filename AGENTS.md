# Project Overview

Apache Sling Scripting SPI is a Java OSGi bundle that defines the service provider interfaces for the Sling scripting layer. It provides two packages: `org.apache.sling.scripting.spi.bundle` (interfaces for bundled/precompiled render units) and `org.apache.sling.api.resource.type` (a `ResourceType` value object for parsing Sling resource type strings). This is a pure API/SPI library with no runtime logic — consumers implement the interfaces, providers discover them via OSGi services.

# Core Commands

```bash
# Build and package
mvn clean install

# Compile only
mvn compile

# Run full test suite
mvn test

# Run a single test class
mvn test -Dtest=ResourceTypeTest

# Run SpotBugs static analysis
mvn spotbugs:check

# Run bnd baseline check (API compatibility)
mvn verify

# Run Spotless formatter check
mvn spotless:check

# Apply Spotless formatting
mvn spotless:apply
```

No dev server — this is a library bundle deployed into an OSGi container (Apache Felix / Sling Launchpad).

# Project Layout

```
pom.xml                          Maven build; parent = sling-bundle-parent:66
src/
  main/java/
    org/apache/sling/
      scripting/spi/bundle/      Core SPI interfaces
        BundledRenderUnit.java   Executed script/precompiled unit contract
        BundledRenderUnitCapability.java  OSGi capability descriptor interface
        BundledRenderUnitFinder.java      Lookup service interface
        TypeProvider.java        Associates a bundle with resource types
        package-info.java        OSGi package version annotation
      api/resource/type/
        ResourceType.java        Value object for parsing resource type strings
        package-info.java        OSGi package version annotation
  test/java/
    org/apache/sling/api/resource/type/
      ResourceTypeTest.java      JUnit 4 tests for ResourceType
target/                          Maven output (do not edit)
  baseline/                      bnd API baseline snapshot
```

# Development Patterns & Constraints

- **Java 17** (`sling.java.version=17` in `pom.xml`).
- **OSGi annotations**: use `org.osgi.annotation.versioning` (`@ConsumerType`, `@ProviderType`) on all public interfaces. Never use Felix SCR annotations.
- **Nullability**: annotate all public API with `@NotNull` / `@Nullable` from `org.jetbrains.annotations`.
- **Servlet API**: this bundle supports both `javax.servlet` (deprecated) and `jakarta.servlet`. New methods must target Jakarta; provide `javax.servlet` overloads only for backward compatibility and mark them `@Deprecated`.
- **Package versioning**: bump the `@Version` in `package-info.java` on any API change. Verify with `mvn verify` (bnd baseline fails the build on incompatible changes).
- **No implementations here**: this repo is SPI only. Do not add concrete implementations.
- Code is formatted via Spotless (enforced in CI). Run `mvn spotless:apply` before committing.
- 4-space indentation, no tabs.

# Git Workflow

- Mirror of the Apache Sling Git repository at `https://gitbox.apache.org/repos/asf/sling-org-apache-sling-scripting-spi.git`.
- Branching: `master` is the main branch. Feature work goes on topic branches; merge via PR to the GitHub mirror.
- Commit messages: short imperative subject line, reference JIRA ticket if applicable (`SLING-XXXXX`).
- PRs require passing CI (Jenkins via `Jenkinsfile`) before merge.
- Do not push directly to `master`.

# Testing Guidelines

- Framework: **JUnit 4** (`junit:junit` test-scoped dependency).
- Test classes live under `src/test/java/` mirroring the main source package structure.
- Run all tests: `mvn test`
- Run one class: `mvn test -Dtest=ClassName`
- Surefire reports land in `target/surefire-reports/`.
- No coverage tooling is configured; do not add one without discussion.
- Since this is a pure SPI bundle, tests focus on value objects (`ResourceType`). Interface tests are not required.

# Gotchas

- **Baseline failures**: any change to a public API (method signature, return type, new required method on a `@ConsumerType`) will fail `mvn verify` via the bnd baseline plugin. Bump the affected package version in its `package-info.java` first.
- **Dual servlet APIs**: `BundledRenderUnit.eval(...)` has two overloads — Jakarta (preferred) and javax (deprecated). The Jakarta default method delegates to the javax abstract method for backward compat. New consumers should implement the javax abstract method; it is called by the Jakarta default. Do not remove the javax overload.
- **No OSGi runtime in tests**: tests run in plain JVM; do not reference `BundleContext` or OSGi framework APIs in test code without mocking.
- The `target/` directory contains a committed baseline JAR snapshot (`target/baseline/`). This is intentional — managed by bnd-baseline-maven-plugin, not by hand.
