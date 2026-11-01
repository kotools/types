---
name: implement
description: Phase 3. Implement the plan exactly as specified.
argument-hint: "<notes>"
disable-model-invocation: true
---

Implement the plan exactly. Take one logical unit at a time.

Ship these together in each unit that changes the public API:

- Tests.
- KDoc samples.
- The refreshed ABI dump.

Follow "Git & Version Control" in CLAUDE.md:

- Local terminal sessions never commit or push. Leave changes unstaged.
- The `@claude` GitHub Action commits each unit separately. It uses
  `<emoji> <message> (<#issue-or-PR>)`. Then it pushes.
- If the context is unclear, do not commit.

$ARGUMENTS
