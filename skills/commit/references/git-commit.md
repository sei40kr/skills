# Committing with git

Commands for each step of the `commit` skill in a git repository.

## 1. Survey

`git status`, `git diff`, `git diff --staged`.

## 3. Fold into unpushed commits

`git log @{upstream}..` lists the unpushed commits. To fold into one, amend or squash with the `git-surgeon` skill.

## 4. Select hunks and verify

Stage with the `git-surgeon` skill. `git add .` / `git add -A` are denied by the harness, and plain `git add <file>` is too coarse for a file with mixed hunks.

`git diff --staged` is the diff that will land; check it against the user's intent.

## 6. Commit

`git commit -m "<message>"`, or the amend variant when folding.

A failed pre-commit hook means the commit did not happen. Fix the issue, re-stage, and retry the same command — do not switch to `--amend`, or you will rewrite the previous commit.

## 7. Confirm

`git status`.
