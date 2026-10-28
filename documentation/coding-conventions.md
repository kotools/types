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

## 📝 Markdown conventions

- Don't use tables, they are hard to maintain; use lists instead.
- Use reference-style links with a `Links` block at the end of file.
