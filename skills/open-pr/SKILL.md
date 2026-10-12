---
name: open-pr
description: Open a GitHub pull request against the right upstream repository, following its PR template and running its PR-triggered workflows locally before opening the browser-based PR creation page. Use whenever the user asks to open, create, or send a pull request.
---

Open a pull request that targets the right repository, follows its PR template, and passes its CI. Use `gh` for everything that touches GitHub.

## 1. Identify the target repository

A fork targets its parent; anything else targets origin. `git remote get-url upstream` names the parent when an `upstream` remote exists; otherwise `gh repo view --json parent,defaultBranchRef` tells whether origin is a fork. The target's default branch is the base unless the user says otherwise.

## 2. Get the branch ready

`git status` and `git log <base>..HEAD` (in jj, `jj status` and `jj log -r '<base>..@'`) must show:

- A clean working copy. Nothing the user expects in the PR is left uncommitted.
- Commits ahead of the base. If the current branch is the base itself, create a branch first: `git switch -c <name>`, or `jj bookmark create <name> -r @-`.
- The branch pushed to the user's fork or origin: `git push -u origin <name>`, or `jj git push --bookmark <name>`. Never push to the upstream repository.

## 3. Locate the PR template

Look in the target repository, not the fork:

- `.github/pull_request_template.md` or `.github/PULL_REQUEST_TEMPLATE.md`
- `.github/PULL_REQUEST_TEMPLATE/`, which holds several; pick the one matching the change, or ask
- `docs/pull_request_template.md`

`gh api repos/{owner}/{repo}/contents/<path>` reads them. When a template exists, the body follows its sections exactly.

## 4. Run PR-triggered workflows locally

For each file in the target's `.github/workflows/` whose `on:` block includes `pull_request`, run what its jobs run:

- Lint and format: the workflow's own command (`biome`, `eslint`, `ruff`, `treefmt`, …)
- Type check: `tsc --noEmit`, `mypy`, `pyright`
- Test and build: the project's test and build commands
- Nix: `nix flake check`

Skip jobs that cannot run locally (secrets, deploys, OS matrices). Report failures before opening the PR and let the user decide whether to fix them or push as is.

## 5. Compose the title and body

Use the `good-writing` skill. Describe every commit in the PR (`git log <base>..HEAD`, `git diff <base>...HEAD`), not the user's request or the latest commit alone.

- Match the language and title convention of the target's recent PRs.
- Fill every section of the template and add none.
- Keep the title under 70 characters; detail goes in the body.

## 6. Open the PR creation page

```
gh pr create --web --repo <target-owner>/<target-repo> --base <base-branch>
```

The user reviews and submits in the browser, so never drop `--web`. The page does not take the title and body from the command; print them in the chat for the user to paste.
