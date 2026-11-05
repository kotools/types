# 🔖 ADR-020: Release type rules for stable and experimental declarations

This document records the decision to define what each type of release contains,
for stable and experimental declarations.

## 🤔 Context

The [versioning strategy] listed which compatibility each type of release may
break. It never said what a release contains. This left three gaps:

- Patch releases could add stable declarations, which [SemVer] reserves for
  minor releases.
- The [declarations lifecycle] allowed adding experimental declarations in any
  type of release, and removing them freely.
- Upgrading API dependencies was governed by the [dependency compatibility]
  documentation only.

## ✅ Decision: Define the content of each type of release

- **Patch** releases only fix bugs in stable declarations. They may change
  experimental declarations, but they don't add, remove or promote them.
- **Minor** releases may add and deprecate declarations, and fix bugs.
- **Major** releases may remove declarations, on top of what minor releases may
  do. They include upgrades of API dependencies.
- Experimental declarations are added, removed or promoted to stable in minor
  and major releases only.
- Existing experimental declarations may change in any type of release, even by
  breaking compatibility.

**Rationale:**

- **Patch releases stay predictable.** Users can upgrade to a patch release
  without getting new API surface. This matches SemVer, where new functionality
  needs a minor release.
- **The rules are stricter than the Kotlin ecosystem.** Kotlin,
  kotlinx.coroutines, kotlinx.serialization and AndroidX let experimental APIs
  change at any time. None of them limits additions to minor releases. We accept
  the stricter rule for safer patch upgrades.
- **Promotion adds a stable declaration.** It follows the same rule as adding
  any other declaration.
- **Removal needs a minor or major release.** An experimental declaration is
  opt-in, but a patch upgrade should still not remove what a client uses.

## 🔗 Consequences

- The versioning strategy and the declarations lifecycle are aligned and link to
  each other.
- The declarations lifecycle shows warning-level deprecation in `X.Y.0` instead
  of `X.Y.Z`, because patch releases don't deprecate declarations.
- [ADR-018] says experimental declarations can be removed directly. That
  statement is outdated, because removal now needs a minor or major release. Its
  decision to remove the overloads stands, so it is not superseded.
- The lifecycle of the `@ExperimentalKotoolsTypesApi` annotation is out of scope
  for this decision.

<!----------------------------------- Links ----------------------------------->

[ADR-018]: ADR-018-integer-division-removal.md
[declarations lifecycle]: ../declarations-lifecycle.md
[dependency compatibility]: ../dependencies.md
[SemVer]: https://semver.org/
[versioning strategy]: ../versioning-strategy.md
