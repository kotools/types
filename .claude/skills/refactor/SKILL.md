---
name: refactor
description: Phase 4. Report refactoring opportunities in branch changes.
context: fork
disable-model-invocation: true
---

Plan mode. Report only. Apply no changes.

Read the code changes on this branch. Report opportunities by category:

- **Reuse**: existing utilities that replace duplicated logic.
- **Simplification**: fewer lines or clearer intent.
- **Efficiency**: better algorithms or structure.
- **Altitude**: higher abstraction, less low-level noise.

For each finding, give the file and line range, the change and the reason.
