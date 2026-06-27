# 🏷️ ADR-009: Explicit serial names for default serializers

This document records how the `SerialDescriptor.serialName` of the default
`kotlinx.serialization` serializers is determined in
`SerializersModule`.

## 🤔 Context

`KotoolsTypesSerializersModule()` provides default
serializers for `EmailAddress` and `EmailAddressRegex`, encoding and decoding
both types as `String`.

Each serializer's `SerialDescriptor` carries a `serialName`. Until now, this
name was computed at runtime by a private reified helper,
`StringSerialDescriptor<T>()`, using `T::class.simpleName` — e.g.
`"EmailAddress"`.

This was fragile for two reasons:

- `KClass.simpleName` is **nullable**, forcing a `checkNotNull` /
  `fail("Serial name can't be null.")` fallback at runtime for a value that
  is, in practice, always known at compile time.
- A bare simple name such as `"EmailAddress"` is not globally unique.
  `kotlinx.serialization` recommends fully-qualified names for
  `SerialDescriptor.serialName` to avoid clashes with other types sharing the
  same simple name elsewhere in a consumer's dependency graph.

## ✅ Decision

Each default serializer declares its `serialName` as an explicit,
fully-qualified `String` literal matching the type's package and class name
(e.g. `"org.kotools.types.EmailAddress"`), instead of deriving it from
`KClass.simpleName`.

These literals are centralized in a private `SerialName` enum, one entry per
serializer:

```kotlin
private enum class SerialName(private val value: String) {
    EmailAddress("org.kotools.types.EmailAddress"),
    EmailAddressRegex("org.kotools.types.EmailAddressRegex");

    override fun toString(): String = this.value
}
```

Each serializer's `descriptor` is now an eagerly-initialized `val` (instead of
a `get()` accessor), built as
`PrimitiveSerialDescriptor(serialName = SerialName.X.toString(), PrimitiveKind.STRING)`.
The reified `StringSerialDescriptor<T>()` helper and its `KClass.simpleName`
lookup are removed entirely.

## 🔗 Consequences

- Serial names are compile-time constants — no reflection, no nullable
  `simpleName`, no runtime `checkNotNull`/`fail` fallback.
- Serial names remain stable under aggressive minification or obfuscation on
  JS and Native, where `KClass.simpleName` is not guaranteed to match the
  source-level class name.
- Adding a default serializer for a new type to
  `KotoolsTypesSerializersModule()` requires adding a
  matching entry to the private `SerialName` enum with the type's
  fully-qualified name, following the same pattern.
- Tests assert against the literal fully-qualified serial name rather than
  `Type::class.simpleName`.
