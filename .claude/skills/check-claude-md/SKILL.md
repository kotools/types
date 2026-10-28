---
name: check-claude-md
description: >-
    Check CLAUDE.md for content derivable from code, then remove it if approved.
disable-model-invocation: true
---

Review the root `CLAUDE.md` and remove what a fresh session could reconstruct
from the repository. The only file you may edit is `CLAUDE.md`.

## Step 1: Read

Read `CLAUDE.md` and note each section. Don't read the files it imports with
`@`: they are separate documents.

## Step 2: Test each section

Ask for each section: could a fresh session rebuild this by reading code,
manifests, CI workflows, or tool output? Prove it against real files before
judging.

Also verify that each fact in the file still matches the repository. Flag stale
facts.

## Step 3: Classify

Cut when derivable. Keep when not derivable. When unsure, keep. Never cut a
"never do X" rule.

## Step 4: Report

List each cut candidate with:

- its line range;
- the verbatim quote;
- the file or command that proves it derivable.

List stale facts separately. If nothing qualifies, say "already lean" and stop.

## Step 5: Ask

Use AskUserQuestion once. Options: apply all cuts (recommended), let me pick,
no changes. After "let me pick", ask one multiSelect question with an option per
candidate.

## Step 6: Apply

Use Edit on `CLAUDE.md`. Change only the approved lines. Fix approved stale
facts in place. Leave all other text and formatting as is.

## Step 7: Verify

Confirm that `git status` shows `CLAUDE.md` as the only change from this skill.

Report the cuts applied and the new line count.

## Pitfalls

- Don't touch other files, local memory, or `~/.claude/CLAUDE.md`.
- Don't move content to other files. Only remove or correct.
- Quote removed text in the report so the cut can be undone.
