# 🎨 Design goals

This section presents the design goals that we keep in mind when building and
improving Kotools Types.

## 🌐 Model real-world concepts

Kotools Types provides types that model real-world concepts with correct and
predictable behavior.

These types are designed to follow their domain definitions, rather than the
limitations of underlying platforms. For example, the `Integer` type represents
a mathematical integer and does not overflow.

By prioritizing correctness, Kotools Types helps developers build APIs that are
easier to reason about and less error-prone.

## 🎯 Be explicit by design

This library favors explicitness over implicit behavior. Its APIs are designed
to make constraints and invariants visible in code, reducing ambiguity and
preventing invalid states.

## 🟢 Provide complete types

Kotools Types provides complete and usable types, not just wrappers. Each type
comes with the operations and APIs necessary to justify its use in real-world
applications.

## 🧱 Avoid useless dependencies

This library is designed to be lightweight and depends only on what is strictly
necessary. See the [dependency compatibility](dependencies.md) documentation for
more details about its dependencies.
