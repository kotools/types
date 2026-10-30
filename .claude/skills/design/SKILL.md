---
name: design
description: >-
    Design a new public API: research standards, mockup, settle decisions,
    document the design in one file
disable-model-invocation: true
argument-hint: "<type or concept to design>"
---

Design the public API for the concept named in `$ARGUMENTS`. This is a
**design-only** task: no implementation, no tests, no samples, no ABI dump, no
ADR. The only output is the design document under
`../../../documentation/design-notes` (today's date, kebab-case slug).

Follow the Markdown conventions of `documentation/coding-conventions.md` in the
document.

## Step 1: Research the standards and use cases

Search the web (RFCs, WHATWG, ISO, W3C, platform docs) for how the concept is
represented. Fetch the primary source whenever a number or rule will end up in
the API, and say so when a figure comes from memory or is derived rather than
quoted. When no external standard applies, say so and cite language sources
instead (Kotlin KEEPs, stdlib, repo precedents). Report:

- the standards or sources: what each one defines, with notes;
- the use cases the API must cover;
- whether the JDK or the Kotlin stdlib already offers a type;
- what the repo already has: search `subprojects/library` for existing
  declarations that the new one replaces, with their callers.

## Step 2: Mockup a minimal API

Show a compact Kotlin signature sketch, and list the design choices to settle
(grammar, equality, normalization, limits, charset).

Apply the deep-module test from `documentation/coding-conventions.md`: reject
pass-through and shallow declarations. A pass-through is accepted only when it
replaces a declaration that gets deprecated.

Propose **at least 3 package options** with a recommendation. Under
`org.kotools.types` for a new type (e.g., `org.kotools.types.number`); for a
replacement, the package of the replaced declarations is an option too.

## Step 3: Settle decisions with the user

Use AskUserQuestion for choices that are the user's to make: recommended option
first, one concern at a time. Always settle these, in this order:

1. the fate of the replaced declarations, if any (deprecate, keep, extend the
   deprecation to related APIs);
2. the package;
3. the stability stage: experimental by default, as in
   `documentation/declarations-lifecycle.md`; stable from the start only as a
   deliberate exception, recorded as such.

Rules:

- Never ask about error messages: their wording is an implementation detail.
  Only the exception type a function throws is part of the design.
- Scan the ADRs in `documentation/decisions/` for precedent before proposing
  semantics.
- After each answer, restate the consolidated decision list and the risks it
  introduces.

## Step 4: Plan, then write the design document

Write the plan (decision list, file, verification) and wait for approval. Then
write the design document, concise but precise, with these sections:

- **Problem**: what is wrong today, with evidence (sources, callers).
- **Decisions**: a numbered list, each item ending with its source (user,
  standard, existing behavior, repo precedent).
- **API**: the signatures as Kotlin code blocks, and `check()` examples. Write
  grammars in Wirth-style EBNF, as in `Integer.parse`. Document the exception
  type, never the message wording.
- **Deep-module check**: why each declaration earns its place.
- **Risks**.
- **Implementation hand-off**: what is left, including the deprecation sequence
  and the internal callers to migrate.
- **Sources**: every source, with the exact section in the link text, such as
  `[RFC 5322 section 3.4.1][rfc5322-3.4.1]`.

Every decision and every source appear in the document and nowhere else. Don't
split a design across several files. Don't link to ADR documents or mention ADR
numbers.

## Step 5: Verify

- Check line length with `awk 'length>80'`: only lines holding a URL may exceed
  it.
- Confirm that each decision and each source is present in the document, and
  that each reference link has a definition in the `Links` block.
- Confirm that `git status` shows only the design document as changed, with
  nothing staged or committed.

Report the document path and what is left for the implementation phase.

## Pitfalls

- Cite a source for every limit and rule; don't guess.
- Answer open questions with a recommendation, not a survey.
- Don't assume the experimental stage: ask.
