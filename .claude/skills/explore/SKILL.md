---
name: explore
description: >-
    Phase 1. Explore the codebase for a GitHub issue, then ask questions.
argument-hint: "<issue-url-or-notes>"
disable-model-invocation: true
---

Exploration mode. Read and analyze only. Make no code edits.

Source of the issue, in order:

1. A GitHub issue URL in `$ARGUMENTS`. Fetch it.
2. A pasted description. Read it.
3. The issue already in this conversation.

Explore the relevant code. Then report:

1. Your understanding of the issue and the required changes.
2. Gaps and ambiguities.
3. Clarifying questions before planning.

$ARGUMENTS
