# 🔂 Declarations lifecycle

## 🤔 Stability stages

This project provides different types of declaration:

- **Experimental** declarations that can be updated freely, even by introducing
  incompatible changes. These declarations are marked with an experimental
  annotation requiring an explicit [opt-in].
- **Stable** declarations that can be used safely, even in most conservative
  scenarios. They follow strictly our [versioning strategy].
- **Deprecated** declarations that is not recommended to use and that will be
  removed in the next **major** release from the API. These declarations are
  marked with the `@Deprecated` annotation from Kotlin.

## 🧬 Evolution principles

Our [versioning strategy] defines which types of release can include each change
below.

### 🧪 Experimentation

A new declaration must be introduced in **experimental** stage for collecting
user feedbacks.

### ⚖️ Stabilization

For being promoted to **stable**, an **experimental** declaration must meet the
following requirements:

- It was introduced in a **minor** release before the current version.
- It doesn't use another **experimental** declaration.

The versions of each stage follow this pattern:

- **Experimental**: `X.Y.0`
- **Stable**: `X.(Y+1).0`
- **Example**: `5.0.0` (_experimental_) => `5.(1+).0` (_stable_)

### 🗑️ Deprecation

A **stable** declaration must be removed incrementally:

1. Deprecate it with a **warning** in a **minor** release.
2. Deprecate it with an **error** in a **minor** release.
3. Hide it from code in a **minor** release.
4. Remove it from code in a **major** release.

The versions of each level follow this pattern:

- **Warning**: `X.Y.0`
- **Error**: `X.(Y+1).0`
- **Hidden**: `X.(Y+2).0`
- **Removed**: `(X+1).0.0`
- **Example**: `5.1.0` (_warning_) => `5.2.0` (_error_) => `5.3.0` (_hidden_) =>
  `6.0.0` (_removed_)

## 🔌 Integrations

Integrations with other tools are designed for **stable** declarations only. An
**experimental** declaration gets none, because it can change or disappear
freely.

Some existing integrations predate this rule and cover **experimental**
declarations. They stay until their declarations are stabilized or removed. No
new one is added for an **experimental** declaration.

<!----------------------------------- Links ----------------------------------->

[opt-in]: https://kotlinlang.org/docs/opt-in-requirements.html
[versioning strategy]: versioning-strategy.md
