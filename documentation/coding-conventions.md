# 🧹 Coding conventions

## 📦 Deep modules in the public API

A public declaration must be a deep module: a simple interface that hides
significant complexity.

It qualifies when it hides at least one of:

- A type's valid range or invariant.
- A multistep construction, validation, or normalization algorithm.
- A platform-specific or implementation-specific detail.

Reject these antipatterns:

- **Pass-through**: delegates to one or two calls, adds no behavior.
- **Name-exposes-everything**: the name reveals what would be hidden.
- **Shallow convenience**: a one- or two-line body behind a trivial interface.

Before proposing a declaration, write the equivalent user code without it. If
that code is short and clear, reject the declaration.

## 🧑‍💻 Kotlin conventions

### 🔤 Style

- Declare explicit types on local variables.
- Omit the type for constructor calls and literals.
- Access members with `this.`.
- Sort imports lexicographically. Aliased imports go last.

### 🧱 Class shape

- Use plain classes with private constructors.
- Use value classes only for private platform code.
- Order members as follows: companion object, equality, operators, conversions.
- Mark `equals`, `hashCode` and `toString` as `final override`.
- Suppress the redundant modality warning on `final` with the string literal
  `"RedundantModalityModifier"`.

### 🏭 Factories

- Provide a throwing factory and an `OrNull` variant.
- Annotate throwing factories with `@JvmStatic`.
- Annotate `OrNull` factories with `@JvmSynthetic`.

### 🧪 Samples

- Name classes `<Type>Sample`. Name Java ones `<Type>JavaSample`.
- Name methods after the documented function.
- Assert with `check()`.

### ✅ Tests

- Name tests as behavior phrases, such as `<function>FailsWith<Case>`.
- Use `repeatTest` for randomized cases.
- Separate test phases with blank lines.

### 📖 KDoc

- Start summaries with a verb.
- Add Kotlin and Java sample blocks through `SAMPLE:`.
- Cross-link throwing and `OrNull` functions.
- Document companions as "Contains class-level declarations for the [X] type".
- End class docs with `@since`.
- Add `@since` on members introduced in a different version than their parent.

### 🗂️ Files

- Name files after the type.
- Name platform files `X.jvm.kt`, `X.js.kt` and `X.native.kt`.

## 📝 Markdown conventions

- Don't use tables, they are hard to maintain; use lists instead.
- Use reference-style links with a `Links` block at the end of file.
