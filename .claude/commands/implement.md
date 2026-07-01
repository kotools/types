---
description: "Phase 3 – implement the plan exactly as specified"
---

Implement exactly the plan. Work through the plan's change breakdown one logical
unit at a time.

For every public API change, ship tests, KDoc samples, and the refreshed ABI
dump together in the same logical unit.

Follow this repository's Git & Version Control policy in CLAUDE.md:

- Local/interactive terminal sessions: never commit or push. Leave all changes
  unstaged in the working tree for the user to review.
- Automated GitHub Action (`@claude` on issues/PRs): commit each logical unit
  separately using gitmoji format `<emoji> <message> (<#issue-or-PR>)`, then
  push.

If it is unclear which context this session is running in, default to not
committing.

$ARGUMENTS
