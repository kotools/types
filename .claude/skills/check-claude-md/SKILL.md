---
name: check-claude-md
description: >-
    Check CLAUDE.md for content derivable from code, then remove it if approved.
disable-model-invocation: true
---

Review the root `CLAUDE.md`. Remove what a fresh session could rebuild from the
repository. Edit only `CLAUDE.md`.

## Step 1: Read

Read `CLAUDE.md`. Note each section. Don't read files it imports with `@`. They
are separate documents.

## Step 2: Test each section

Ask for each section: could a fresh session rebuild this? It could use code,
manifests, CI workflows, or tool output. Prove it against real files.

Check each fact against the repository. Flag stale facts.

## Step 3: Classify

Cut derivable content. Keep the rest. If unsure, keep. Never cut a "never do X"
rule.

## Step 4: Report

List each cut candidate with:

- its line range;
- the verbatim quote;
- the file or command that proves it derivable.

List stale facts separately. If nothing qualifies, say "already lean". Then
stop.

## Step 5: Ask

Use AskUserQuestion once. Options: apply all cuts, let me pick, no changes. Mark
the first option as recommended. After "let me pick", ask one multiSelect
question. Give it one option per candidate.

## Step 6: Apply

Use Edit on `CLAUDE.md`. Change only the approved lines. Fix approved stale
facts in place. Leave all other text and formatting as is.

## Step 7: Verify

Confirm that `git status` shows `CLAUDE.md` as the only change from this skill.

Report the cuts applied. Report the new line count.

## Pitfalls

- Don't touch other files, local memory, or `~/.claude/CLAUDE.md`.
- Don't move content to other files. Only remove or correct.
- Quote removed text in the report. This lets the user undo the cut.
