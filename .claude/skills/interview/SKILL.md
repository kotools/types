---
name: interview
description: >-
    Interview the user about a plan, decision, or idea. Use when the user wants
    to stress-test their thinking, or uses any 'interview' trigger phrases.
argument-hint: "<topic>"
---

Interview the user until you reach a shared understanding. Map this as a
**design tree**. Every decision branches into the decisions that hang off it.

Work the tree in **rounds**. The **frontier** is every decision whose
prerequisites are settled. These are the questions you can ask _now_. Do not
guess answers you haven't heard yet. Ask the whole frontier in one round. Number
each question and give your recommended answer. Then wait for the user's
answers.

Format a round like so:

```
❓ **Q1** **<question title>**: <question body>

➡️ <your recommended answer>

---

❓ **Q2** **<question title>**: <question body>

➡️ <your recommended answer>
```

Question body might have multiple paragraphs and can include multiple choices.

Word each question so "yes" accepts your recommended answer.

Each answered round reshapes the tree. Settled decisions push the frontier
outward. They unblock questions that depended on them. Recompute the frontier
and ask the next round. A question that depends on another open question belongs
to a _later_ round.

Finding _facts_ is your job, never the user's. When a question needs a fact from
the filesystem or tools, dispatch a sub-agent with a fresh context window. Don't
ask the user for anything you could look up. Don't block on it. A running
exploration is an unsettled prerequisite. Only downstream questions wait for the
sub-agent. Ask the rest of the frontier now. The _decisions_ are the user's. Put
each to them and wait.

The session is done when the frontier is empty. Every branch of the design tree
is visited. Nothing is left silently assumed. Do not implement anything until
the user confirms you have reached a shared understanding.
