---
name: commit
description: Create a commit with hunk-level selection, diff-scope verification, and sandbox-bypassed signed commit execution — with jj in a jujutsu repository, git otherwise. Use whenever the user asks for a commit, especially when the working tree contains a mix of related and unrelated changes.
allowed-tools:
  - Bash(jj root:*)
  - Bash(jj status:*)
  - Bash(jj diff:*)
  - Bash(jj log:*)
  - Bash(jj show:*)
  - Bash(jj commit:*)
  - Bash(jj squash:*)
  - Bash(jj describe:*)
  - Bash(jj fix:*)
  - Bash(jj undo:*)
  - Bash(jj-hunk-tool hunks:*)
  - Bash(jj-hunk-tool patch:*)
  - Bash(jj-hunk-tool split:*)
  - Bash(jj-hunk-tool squash:*)
  - Bash(git status:*)
  - Bash(git diff:*)
  - Bash(git log:*)
  - Bash(git-surgeon hunks:*)
  - Bash(git-surgeon stage:*)
  - Bash(git-surgeon unstage:*)
  - Bash(git-surgeon split:*)
  - Bash(git-surgeon amend:*)
  - Bash(git-surgeon squash:*)
  - Bash(git-surgeon fold:*)
  - Bash(git-surgeon reword:*)
  - Bash(git commit:*)
---

Keep unrelated changes out of the commit, and make sure it is signed.

## Pick the tool

Run `jj root`. If it succeeds the repository is managed by jujutsu: use `jj` with the `jj-surgeon` skill and follow [references/jj-commit.md](references/jj-commit.md). Otherwise use `git` with the `git-surgeon` skill and follow [references/git-commit.md](references/git-commit.md). The rules below are the same for both; the reference docs map each step to that tool's commands. In a colocated repository (`.jj` next to `.git`) still use `jj` — writing through `git` leaves jj to reconcile afterwards.

## 1. Survey the working tree

Enumerate everything that could land in the commit. If nothing would, say so; never create an empty commit.

## 2. Review before selecting

Run the `review` skill unless the change already went through it this session. Its fixes belong in this commit, so they have to land before anything is selected.

## 3. Fold into unpushed commits

If the change belongs with a commit that is not pushed yet, fold it into that commit instead of stacking a new one on top — and revisit that commit's message, since the fold may change what it does. Never rewrite pushed history.

## 4. Select hunks and verify

Select exactly the hunks that belong to the change; whole-file selection is too coarse when a file mixes related and unrelated hunks. Leave out files that likely hold secrets (`.env`, credentials, tokens) even when they changed; if the user asks for them by name, warn before continuing. Then compare the diff that will land against the user's intent. If anything unrelated crept in, surface it and confirm before continuing.

## 5. Write the message

Use the `good-writing` skill. Describe the selected diff, not the request. The reader has no access to the conversation — drop task framing and context that lives only in the prompt.

Follow the repository's own convention when it has one — check CONTRIBUTING, a commitlint config, and the style of recent history. When the repository specifies none, write an Angular conventional commit as described in [references/angular-conventional-commits.md](references/angular-conventional-commits.md).

## 6. Commit outside the sandbox

Run the commit command with `dangerouslyDisableSandbox: true`. Signing needs gpg-agent, which the sandbox blocks — inside it the commit fails or silently lands unsigned.

## 7. Confirm

Verify the commit landed and the working copy is in the expected state.
