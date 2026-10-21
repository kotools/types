---
name: design
description: >-
    Design a new public API: research standards, mockup, settle decisions, write
    documented stubs
disable-model-invocation: true
argument-hint: "<type or concept to design>"
---

Design the public API for the concept named in `$ARGUMENTS`. This is a
**design-only** task: no implementation, no tests, no samples, no ABI dump, no
ADR, and no edits outside `subprojects/design`.

## Step 1: Research the standards and use cases

Search the web (RFCs, WHATWG, ISO, W3C, platform docs) for how the concept is
represented. Fetch the primary source whenever a number or rule will end up in
the API, and say so when a figure comes from memory or is derived rather than
quoted. Report:

- a table of the standards: what each one defines, with notes;
- the use cases the API must cover;
- whether the JDK or the Kotlin stdlib already offers a type;
- what the repo already has: search `subprojects/library` for an existing
  declaration that the new one replaces.

## Step 2: Mockup a minimal API

Show a compact Kotlin signature sketch, and list the design choices to settle
(grammar, equality, normalization, limits, charset).

Apply the deep-module test from `documentation/coding-conventions.md`: reject
pass-through and shallow declarations.

Propose **at least 3 package options** under `org.kotools.types`, with a
recommendation (e.g., `org.kotools.types.number`).

## Step 3: Settle decisions with the user

Use AskUserQuestion for choices that are the user's to make: recommended option
first, one concern at a time.

- Never ask about error messages: their wording is an implementation detail.
  Only the exception type a function throws is part of the design.
- Scan the ADRs in `documentation/decisions/` for precedent before proposing
  semantics.
- After each answer, restate the consolidated decision table and the risks it
  introduces.

## Step 4: Check the design module

Confirm the dependencies of the module `types-design`. It must not apply
samples, documentation of ABI plugin.

## Step 5: Plan, then write the stubs

Write the plan (decision table, files, verification) and wait for approval.
Then create `subprojects/design/src/commonMain/kotlin/<package path>/<Type>.kt`:

- explicit visibility on every declaration, annotated with
  `@ExperimentalKotoolsTypesApi`, with 80-column lines;
- bodies are `= TODO()` (getters are `get() = TODO()`);
- `@Suppress("UNUSED_PARAMETER")` on TODO-bodied functions with parameters,
  the one suppression allowed under `-Werror`;
- Java annotations as on `Integer.parse` and `Integer.parseOrNull`
  (`@JvmStatic`, and `@JvmSynthetic` for nullable returns).

### KDoc rules for design-stage types

- Class KDoc uses `<details>` sections: Motivations, Grammar (Wirth-style EBNF
  as in `Integer.parse`), Limits, other policies (e.g. case handling), Out of
  scope, Standards (every source), Key features.
- Every decision of the session appears in the KDoc, with links to the exact RFC
  or spec sections.
- Use **classic inline Markdown links** with the section in the text, such as
  `[RFC 5322 section 3.4.1](https://...)`. Never use reference-style links
  (`[text][ref]` plus a definition list): IntelliJ IDEA doesn't resolve them. A
  line holding a long URL may exceed 80 columns, since a URL can't be wrapped.
- No `@since` tags.
- Examples are **inline** fenced Kotlin blocks that assert with `check()`.
- Describe behavior only: no links to ADR documents, no ADR numbers.
- Document the exception type, never the message wording.

## Step 6: Verify

- Run `./gradlew :types-design:build`.
- Check line length with `awk 'length>80'`: only lines holding a URL may exceed
  it.
- Confirm that each decision and each source link is present in the KDoc.
- Confirm that `git status` shows only `subprojects/design/**` changed, with
  nothing staged or committed.

Report the stub path and what is left for the implementation phase.

## Pitfalls

- Cite a source for every limit and rule; don't guess.
- Answer open questions with a recommendation, not a survey.
