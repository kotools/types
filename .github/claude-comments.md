# Comment templates for working with Claude

Copy-paste GitHub Issue comments that invoke Claude Code skills via
`@claude` mentions. Post them in the order below.

## Step 1: Resolve

```markdown
@claude /resolve-issue
```

## Step 2: Answer questions (optional)

Post this after Step 1 if Claude asked clarifying questions.
Replace each placeholder answer before posting.

```markdown
@claude Still in plan mode, read answers below to your questions.

**Question 1:** ANSWER
```

## Step 3: Revise plan (optional)

Post this after Claude presented its plan if you want changes.
Fill in the sections before posting.

```markdown
@claude Update your plan accordingly to comments below.

**Source code changes:** ...

**Documentation updates:** ...
```

## Step 4: Approve plan

Post this once the plan is right. Claude then implements it.

```markdown
@claude Plan approved. Implement it.
```

## Step 5: Refactor (optional)

```markdown
@claude /refactor
```
