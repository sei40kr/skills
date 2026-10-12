---
name: review
description: Review a code change and fix what the review finds. Use whenever the user asks to review, check, or look over a diff, a commit, or a pull request.
---

Review the change the user points at — by default the working tree diff (`git diff` plus `git diff --staged`). Read the whole diff, then apply each rule below. Fix what you find and tell the user what changed and why.

## Rules

- **Code comments and docstrings** — every one added or changed in the diff must satisfy the `writing-code-comments` skill. Delete or rewrite those that don't.
