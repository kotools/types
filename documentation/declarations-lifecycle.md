# 🔂 Declarations lifecycle

## 🤔 Stability stages

This project provides different types of declaration:

- **Experimental** declarations that can be updated or removed freely from the
  Application Programming Interface (API), even by introducing incompatible
  changes.
  These declarations are marked with an experimental annotation requiring an
  explicit [opt-in].
- **Stable** declarations that can be used safely, even in most conservative
  scenarios, and that follow strictly our
  [versioning strategy](versioning-strategy.md).
- **Deprecated** declarations that is not recommended to use and that will be
  removed in the next **major** release from the API.
  These declarations are marked with the [Deprecated][kotlin.Deprecated]
  annotation from Kotlin.

## 🧬 Evolution principles

### 🧪 Experimentation

A new declaration should be introduced in **experimental** stage for collecting
user feedbacks. This change can be included in any type of release.

### ⚖️ Stabilization

An **experimental** declaration can be promoted to **stable** by removing the
experimental annotation marking it that requires an explicit [opt-in]. This
change can be included in the second **minor release** after its introduction,
or later.

| Stage       | Experimental | Stable      | Example                 |
|-------------|--------------|-------------|-------------------------|
| **Version** | `X.Y.Z`      | `X.(Y+2).0` | `5.0` -> `5.2` or later |

Note that an **experimental** declaration shouldn't be promoted to **stable** if
its implementation uses another **experimental** declaration.

### 🗑️ Deprecation

A **stable** declaration introduced should be removed by:

- Deprecating it with a [warning level][kotlin.DeprecationLevel.WARNING] in a
  **minor release**.
- Deprecating it with an [error level][kotlin.DeprecationLevel.ERROR] in a
  **minor release**.
- Deprecating it with a [hidden level][kotlin.DeprecationLevel.HIDDEN] in a
  **minor release**.
- Removing it in a **major release**.

| Deprecation level | Warning | Error       | Hidden      | Removed     |
|-------------------|---------|-------------|-------------|-------------|
| **Version**       | `X.Y.Z` | `X.(Y+1).0` | `X.(Y+2).0` | `(X+1).0.0` |

<!-- Links -->

[kotlin.Deprecated]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-deprecated
[kotlin.DeprecationLevel.ERROR]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-deprecation-level/-e-r-r-o-r.html
[kotlin.DeprecationLevel.HIDDEN]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-deprecation-level/-h-i-d-d-e-n.html
[kotlin.DeprecationLevel.WARNING]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-deprecation-level/-w-a-r-n-i-n-g.html
[opt-in]: https://kotlinlang.org/docs/opt-in-requirements.html
