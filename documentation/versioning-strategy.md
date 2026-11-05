# 🔂 Versioning strategy

For versioning Kotools Types, we have an approach based on backward
compatibility.

See our [declarations lifecycle] for the stages that declarations go through.

> [!NOTE]
> Each rule in this document is expressed using an [RFC 2119] keyword.

## 🧩 Types of compatibility

We focus on three types of compatibility:

- **Behavioral**: A declaration MUST produce the same observable result for the
  same input in a newer version.
- **Source**: Client's code that compiles against a version MUST recompile
  without error against a newer version.
- **Binary**: Client's code compiled against a version MUST run against a newer
  version, without loading or linkage error.

A release breaks a type of compatibility when it doesn't satisfy the rule of
that type.

## ⚖️ Stable API

With these types of compatibility in mind, here are the different types of
release that we ship for the stable API of Kotools Types:

- **Patch (`X.Y.Z`)**:
    - MUST only fix bugs in existing stable declarations.
    - MAY change existing experimental declarations, except removing them.
    - MAY break **behavioral** compatibility for fixing bugs.
    - MUST NOT break **source** and **binary** compatibilities.
- **Minor (`X.Y.0`)**:
    - MAY add or deprecate declarations, and fix bugs.
    - MAY break **behavioral** compatibility for fixing bugs.
    - MAY break **source** compatibility for deprecating declarations.
    - MUST NOT break **binary** compatibility.
- **Major (`X.0.0`)**:
    - MAY remove declarations, on top of what a minor release MAY do.
    - MUST include upgrades of API dependencies.
    - MAY break **behavioral** compatibility for fixing bugs.
    - MAY break **source** compatibility for deprecating declarations.
    - MAY break **binary** compatibility for removing declarations.

## 🧪 Experimental API

We also ship an experimental API for trying out ideas and collecting feedbacks.
It includes declarations marked with `@ExperimentalKotoolsTypesApi`.

This API doesn't have the same compatibility policy as the stable one:

- Experimental declarations MAY be added or removed in **minor** and **major**
  releases only.
- Existing experimental declarations MAY change in any type of release. ***These
  changes can break any type of compatibility!***
- Promoting an experimental declaration to stable MUST happen in **minor** and
  **major** releases only.

<!----------------------------------- Links ----------------------------------->

[declarations lifecycle]: declarations-lifecycle.md
[RFC 2119]: https://datatracker.ietf.org/doc/html/rfc2119
