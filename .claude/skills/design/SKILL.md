---
name: design
description: >-
    Write a design note for a new public API of this library. Use when the user
    wants to design a type or function, or uses any 'design' trigger phrases
    about the public API.
argument-hint: "<concept>"
---

Write one design note for the concept named in `$ARGUMENTS`. Put it under
`documentation/design-notes`. Name it with today's date and a kebab-case slug.

## Research

Cite primary sources for every number or rule. Say so when a figure comes from
memory or is derived. Without an external standard, cite language sources and
repo precedents. Search `subprojects/library` for declarations that the new one
replaces, with their callers.

## Mockup

Sketch a minimal Kotlin API and list the design choices to settle. Apply the
deep-module test. A pass-through is accepted only when it replaces a declaration
that gets deprecated.

## Decide

Settle the open choices by invoking the `interview` skill.

- New declarations are experimental. Ask whether the design is a stable
  exception.
- Never ask about error messages. Only the exception type is part of the design.

## Write

Write a plan and wait for approval. Then write the note with these sections:
Problem, Decisions, API, Deep-module check, Risks, Implementation hand-off and
Sources.

- Each decision ends with its source.
- Record a stable exception as such in Decisions.
- Write grammars in Wirth-style EBNF.
- Hand-off lists the deprecation sequence and the callers to migrate.
- Source link text names the exact section.

Don't link to ADRs or mention their numbers.

## Verify

Confirm that each decision and source is in the note. Confirm that each
reference link has a definition in the `Links` block.
