---
name: resolve-issue
description: Resolve a GitHub issue. Explore, interview, plan, then implement.
argument-hint: "<github-issue-url>"
disable-model-invocation: true
---

Resolve the issue in four steps. Finish each before starting the next.

## 1. Explore

Read and analyze only. Make no code edits.

Source of the issue, in order:

1. A GitHub issue URL in `$ARGUMENTS`. Fetch it with `gh issue view`.
2. A pasted description. Read it.
3. The issue already in this conversation.

Explore the relevant code. Then summarize:

- Your understanding of the issue and the required changes.
- Gaps and ambiguities.

## 2. Interview

Invoke the `interview` skill on the gaps and ambiguities. Do not move on until
the user confirms a shared understanding.

## 3. Plan

Make no code edits. Write the plan from the exploration and the answers.

Plan one change, not several logical units. Present the plan. Stop and wait for
approval.

## 4. Implement

After approval, implement the plan exactly.
