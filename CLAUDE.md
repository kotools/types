# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working
with this repository.

## What this project is

Kotools Types is a Kotlin Multiplatform library providing types that model
real-world concepts with correct behavior (e.g., an `Integer` that doesn't
overflow, `EmailAddress` with validated format). It targets JVM, JS, and
Kotlin/Native platforms.

Current version: `5.2.0-SNAPSHOT` (`gradle.properties`).

## Commands

```shell
# Check the main library on all platforms
# (runs tests + ABI check + sample validation)
./gradlew :types:check

# Check only the Gradle convention plugins
./gradlew :plugins:check

# Build individual subprojects
./gradlew :types-internal:build
./gradlew :types:build
./gradlew :types-qr-code:build

# Generate API documentation (Dokka)
./gradlew :assemble

# Update the ABI dump after adding/changing public API
./gradlew :types:apiDump

# Run a single test class (JVM only)
./gradlew :types:jvmTest --tests "org.kotools.types.EmailAddressTest"
```

Requires JDK 17 (Temurin recommended). Native targets require macOS.

## Repository structure

```
gradle/plugins/     Convention + custom Gradle plugins (included build)
subprojects/
  internal/         types-internal – internal utilities, not public API
  library/          types – the main published library
  qr-code/          types-qr-code – JVM-only QR code generator (tooling)
documentation/      Design docs (design-goals, versioning-strategy,
                    declarations-lifecycle)
```

### Source sets in `subprojects/library`

| Source set      | Purpose                                                     |
|-----------------|-------------------------------------------------------------|
| `commonMain`    | Shared public API (`org.kotools.types` and `kotools.types`) |
| `jvmMain`       | JVM-specific `actual` implementations                       |
| `jsMain`        | JS-specific `actual` implementations                        |
| `nativeMain`    | Native-specific `actual` implementations                    |
| `commonTest`    | Shared tests **and** code samples used in KDoc              |
| `jvmNativeTest` | Tests shared between JVM and Native                         |
| `jvmTest`       | JVM-specific tests; also Java sample files (`*.java`)       |
| `jsTest`        | JS-specific samples                                         |

`subprojects/internal` mirrors this layout with the same source set names.

## Two API packages

- `org.kotools.types` – newer types introduced from v4.4+, currently
  experimental (`Integer`, `EmailAddress`, `EmailAddressRegex`). Requires
  `@ExperimentalKotoolsTypesApi` opt-in.
- `kotools.types.*` – older stable types (`NotBlankString`,
  `NotEmptyList/Set/Map`, integer variants like `StrictlyPositiveInt`).

Internal utilities live under `org.kotools.types.internal` and
`kotools.types.internal`, protected by `@InternalKotoolsTypesApi`. The
`types-internal` subproject is a compile-time dependency of `types` but is
never exposed publicly.

## Samples system

KDoc examples are not written inline. They reference test functions using:

```kotlin
// In KDoc of a public declaration:
SAMPLE: org.kotools.types.number.IntegerSample.plus
```

Sample functions live in test source sets in files named `*Sample.kt` (or
`*Sample.java` for JVM Java samples). The custom `kotools-samples` Gradle
plugin (`gradle/plugins/src/main/kotlin/org/kotools/samples/`) extracts and
inlines them during documentation generation, and validates references during
`check`. When adding a new public API function, add a matching sample function
in the appropriate `*Sample` class.

## ABI compatibility

Binary compatibility is enforced by `kotlinx-bcv`. The ABI dump lives in
`subprojects/library/src/api/`. If `check` fails due to an ABI mismatch
after changing a public declaration, run `./gradlew :types:apiDump` to
regenerate it and commit the updated `.api` files.

## Kotlin compilation constraints

- **Explicit API mode** is on: every public declaration needs an explicit
  visibility modifier.
- **`allWarningsAsErrors`** is enabled for both Gradle Kotlin DSL
  (`gradle.properties`) and all Kotlin compilations. Use `@Suppress` only
  when unavoidable.

## Declaration lifecycle

New public declarations must start as **experimental** (annotated with
`@ExperimentalKotoolsTypesApi`). Stable declarations are deprecated in three
steps (WARNING → ERROR → HIDDEN across minor releases) before removal in a
major release. Never remove a stable declaration in a single step. See
`documentation/declarations-lifecycle.md` for the full policy.

## Articles

Community-facing articles (Reddit, Slack) live in `documentation/articles/`.
See `documentation/articles/README.md` for naming conventions and distribution
strategy.

## Development Workflow

**Trunk-based development.** Every change must keep `main` green on its own —
it must compile and pass `check` (tests + ABI check + sample validation) when
merged. Enforce this by:

- **Ship tests, samples, and the ABI dump in the same change as the feature.**
  A public API change without a refreshed `.api` file fails `apiCheck` on
  trunk. A new sample reference without a matching sample function fails
  `check`.
- **CI wiring travels with the change it enables**, not as a follow-up
  cleanup.

### Commit conventions for related API additions

When an issue adds multiple related public API members (e.g. several
`from<Type>` / `to<Type>` conversions), follow these conventions:

- **One commit per public API member.** Each commit that adds or changes a
  public API member ships its KDoc, Kotlin/Java samples, tests, and ABI dump
  update together.
- **Split closely-related pairs into separate commits.** A conversion and its
  `OrNull` counterpart (e.g. `toLong()` and `toLongOrNull()`) are each their
  own public API addition and get their own commit, even though they're
  conceptually paired.
- **Separate ADR documentation from changelog updates.** When an issue
  requires both an Architecture Decision Record (ADR) and an unreleased
  `CHANGELOG.md` update, these are two separate commits, with the ADR landing
  first, followed by the changelog update.

## Git & Version Control

- **Never commit or push in interactive/local sessions.** Do not run
  `git commit`, `git push`, or any history-altering git command. Make the
  requested edits and leave them in the working tree (unstaged) for the user
  to review and commit themselves. This applies even if a task seems
  complete or the user previously approved a plan.
- **Exception: automated GitHub workflows.** When running as the GitHub
  Action responding to `@claude` mentions on issues and pull requests
  (`.github/workflows/claude.yml`), committing and pushing is required to
  deliver changes as a PR. In that context, commit messages follow the
  Gitmoji convention from `CONTRIBUTING.md`:

  ```text
  <intention> <message> (<identifier>)
  ```

  - `intention`: an emoji from [Gitmoji](https://gitmoji.dev) describing the
    type of change.
  - `message`: a brief explanation of the change.
  - `identifier`: a reference to the issue, discussion, or PR (e.g. `#988`).

  The Action may also `git fetch` and `git rebase` onto `main`, then
  force-push, but only on its **own** `claude/issue-*` branches (e.g. to
  pick up commits that landed on `main` after the branch was created).
  Never rebase or force-push `main` itself.
