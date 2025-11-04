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

A new declaration must be introduced in **experimental** stage for collecting
user feedbacks. This change can be included in any type of release.

### ⚖️ Stabilization

For being promoted to **stable**, an **experimental** declaration must meet the
following requirements:

- It was introduced in or before the latest **minor** release before the
  current version. For example, an **experimental** declaration introduced in
  version `5.0` can be stabilized in or after version `5.1`.
- It doesn't use another **experimental** declaration.

Stabilizing a declaration must be included in a **minor** or **major** release.

| Stage       | Experimental | Stable      | Example         |
|-------------|--------------|-------------|-----------------|
| **Version** | `X.Y.Z`      | `X.(Y+1).0` | `5.0` -> `5.1+` |

### 🗑️ Deprecation

A **stable** declaration must be removed incrementally by addressing the
following steps:

1. Deprecate it with a [warning level][kotlin.DeprecationLevel.WARNING] in a
   **minor** release.
2. Deprecate it with an [error level][kotlin.DeprecationLevel.ERROR] in a
   **minor** release.
3. Deprecating it with a [hidden level][kotlin.DeprecationLevel.HIDDEN] in a
   **minor** release.
4. Removing it in a **major** release.

| Deprecation level | Warning | Error       | Hidden      | Removed     |
|-------------------|---------|-------------|-------------|-------------|
| **Version**       | `X.Y.Z` | `X.(Y+1).0` | `X.(Y+2).0` | `(X+1).0.0` |

<!----------------------------------- Links ----------------------------------->

[kotlin.Deprecated]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-deprecated
[kotlin.DeprecationLevel.ERROR]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-deprecation-level/-e-r-r-o-r.html
[kotlin.DeprecationLevel.HIDDEN]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-deprecation-level/-h-i-d-d-e-n.html
[kotlin.DeprecationLevel.WARNING]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-deprecation-level/-w-a-r-n-i-n-g.html
[opt-in]: https://kotlinlang.org/docs/opt-in-requirements.html
