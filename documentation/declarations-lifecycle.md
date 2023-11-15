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

- A new declaration should be introduced in **experimental** stage for
  collecting user feedbacks.
- An **experimental** declaration shouldn't be promoted to **stable** if its
  implementation uses another **experimental** declaration.
- An **experimental** declaration could be promoted to **stable** when releasing
  a new **minor** or **major** version by:
    1. removing the experimental annotation marking it that requires an explicit
       [opt-in]
    2. replacing the `ExperimentalSinceKotoolsTypes` annotation by
       the `SinceKotoolsTypes` annotation with an updated version value.
- An **experimental** declaration could be updated or removed freely in next
  **minor** or **major** versions.
- A **stable** declaration introduced in the version `X.Y.Z` should be removed
  by:
    1. deprecating it in the version `X.(Y + 1).0`
    2. removing it in the version `(X + 1).0.0`.
