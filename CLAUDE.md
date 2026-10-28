# CLAUDE.md

## General

### Core Rules

Short sentences only (8-10 words max). No filler, no preamble, no pleasantries.
Tool first. Result first. Don't explain unless asked. Code stays normal. English
gets compressed.

### Formatting

Output sounds human. Never AI-generated. Never use em-dashes or replacement
hyphens. Avoid parenthetical clauses entirely. Hyphens map to standard grammar
only.

### Think Before Coding

**Don't assume. Don't hide confusion. Surface tradeoffs.**

- State your assumptions explicitly. If uncertain, ask.
- If multiple interpretations exist, present them. Don't pick silently.
- If a simpler approach exists, say so. Push back when warranted.
- If something is unclear, stop. Name what's confusing. Ask.

### Simplicity and Surgical Changes

**Minimum code that solves the problem. Touch only what you must.**

- No features, abstractions, or configurability beyond what was asked.
- No error handling for impossible scenarios.
- Don't improve, refactor, or reformat adjacent code. Match existing style.
- Mention unrelated dead code, don't delete it.
- Remove orphans that your own changes created.

Every changed line must trace directly to the user's request.

### Goal-Driven Execution

**Define success criteria. Loop until verified.**

Use TDD:

1. Write missing tests. These might fail at this step.
2. Fix failing tests with minimum production code changes.

For multistep tasks, state a brief plan:

```
1. [Step] -> verify: [check]
2. [Step] -> verify: [check]
```

## Project

### Overview

See:

- @.editorconfig, @documentation/coding-conventions.md and
  @documentation/design-goals.md for coding conventions.
- `documentation/declarations-lifecycle.md` for the declarations' lifecycle.

### Trunk-based development

Every change must keep `main` green on its own: it must compile and pass `check`
when merged. Enforce this by:

- **Ship tests, samples, and the ABI dump in the same change as the feature.**
- **CI wiring travels with the change it enables**, not as a follow-up cleanup.

### Git & Version Control

**Never commit or push.** Do not run `git commit`, `git push`, or any
history-altering git command. Make the requested edits and leave them in the
working tree (unstaged) for the user to review and commit themselves.

Exception: the GitHub Action responding to `@claude` mentions must commit and
push to deliver a PR. Commit messages follow the Gitmoji format in
`CONTRIBUTING.md`. It may rebase and force-push only its own `claude/issue-*`
branches.
