# Comment templates for working with Claude

Copy-paste GitHub Issue comments that invoke Claude Code skills via
`@claude` mentions. Post them in the order below.

## Step 1 — Explore

```markdown
@claude /explore
```

## Step 2 — Answer questions (optional)

Post this after Step 1 if Claude asked clarifying questions.
Replace each placeholder answer before posting.

```markdown
@claude Still in plan mode, read answers below to your questions.

**Question 1:** ANSWER
```

## Step 3 — Plan

```markdown
@claude /plan
```

## Step 4 — Revise plan (optional)

Post this after Step 3 if you want changes to the plan.
Fill in the sections before posting.

```markdown
@claude Update your plan accordingly to comments below.

**Source code changes:** ...

**Documentation updates:** ...

**Commit breakdown:** ...
```

## Step 5 — Implement

```markdown
@claude /implement
```

## Step 6 — Refactor (optional)

```markdown
@claude /refactor
```
