# Declarations lifecycle

## Stability stages

This project provides different type of declarations:

- **Experimental** declarations that can be updated or removed freely from the
  API, even by introducing incompatible changes.
  These declarations are marked with an experimental annotation requiring an
  explicit [opt-in].
- **Stable** declarations that can be used safely, even in most conservative
  scenarios, and that follow strictly our
  [versioning strategy](versioning-strategy.md).
- **Deprecated** declarations that is not recommended to use and that will be
  removed in the next **major** release from the API.
  These declarations are marked with the [Deprecated][kotlin.Deprecated]
  annotation from Kotlin.

[kotlin.Deprecated]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-deprecated
[opt-in]: https://kotlinlang.org/docs/opt-in-requirements.html

## Evolution principles

The evolution of declarations provided by this project should respect the
following principles:

### Experimental declarations

A new declaration should be introduced in **experimental** stage for collecting
user feedbacks.

An **experimental** declaration shouldn't be promoted to **stable** if its
implementation uses another **experimental** declaration.

An **experimental** declaration can be promoted to **stable** by removing the
experimental annotation marking it that requires an explicit [opt-in].

An **experimental** declaration can be updated or removed freely in any type of
releases.

### Stable declarations

A **stable** declaration introduced in the version `X.Y.Z` should be removed by:
- deprecating it with a [warning level][kotlin.DeprecationLevel.WARNING] in the
  version `X.(Y + 1).0`
- deprecating it with an [error level][kotlin.DeprecationLevel.ERROR] in the
  version `X.(Y + 2).0`
- deprecating it with a [hidden level][kotlin.DeprecationLevel.HIDDEN] in the
  version `X.(Y + 3).0`
- removing it in the version `(X + 1).0.0`.

[kotlin.DeprecationLevel.ERROR]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-deprecation-level/-e-r-r-o-r.html
[kotlin.DeprecationLevel.HIDDEN]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-deprecation-level/-h-i-d-d-e-n.html
[kotlin.DeprecationLevel.WARNING]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-deprecation-level/-w-a-r-n-i-n-g.html
