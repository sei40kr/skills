# Committing with jujutsu

Commands for each step of the `commit` skill in a jj repository.

The working copy is itself a commit, `@`, and every jj command snapshots it first. There is no index: "staging" means deciding which hunks leave `@` as the new commit; the rest stays in `@`. Hunk-level selection goes through the `jj-surgeon` skill and its `jj-hunk-tool` CLI — jj's own hunk selection is interactive only. Pass `--git --no-pager` when viewing diffs so nothing opens a pager.

## 1. Survey

`jj status`, `jj diff --git --no-pager`, and `jj-hunk-tool hunks` to list every hunk in `@` with its ID.

## 3. Fold into unpushed commits

`jj log --no-pager -r '::@- ~ ::remote_bookmarks()'` lists the commits no remote bookmark points at.

To fold, move the related hunks into that commit with the `jj-surgeon` skill:

```
jj-hunk-tool squash <hunk-id>... --into <rev> -m "<revised full message>"
```

Always pass `-m` with the complete, revised message: without it jj either keeps the old description or opens an editor, which hangs the session. When whole files belong, `jj squash --into <rev> -m "<message>" -- <paths>` does the same by path.

## 4. Select hunks and verify

Pick the hunk IDs from `jj-hunk-tool hunks`; a line range (`<id>:3-9`) narrows a hunk that mixes related and unrelated lines. `jj-hunk-tool patch <hunk-id>...` prints the diff that will land; check it against the user's intent before committing.

When whole files belong, `jj diff --git --no-pager -- <paths>` is the diff that will land. When everything in `@` belongs, `jj diff --git --no-pager`.

## 6. Commit

The command depends on what was selected:

- Selected hunks: `jj-hunk-tool split <hunk-id>... -m "<message>"`. The selected hunks become the new commit `@-`; the rest stays in `@`.
- Whole files: `jj commit -m "<message>" -- <paths>`.
- Everything in `@`: `jj commit -m "<message>"`.

Always pass `-m`: without it jj opens an editor, which hangs the session. jj signs the commit itself when `signing.behavior` is configured, and `jj-hunk-tool` drives jj underneath, so the sandbox rule applies to all three commands.

jj has no commit hooks, so there is no failed-hook case to recover from. If something unrelated landed anyway, `jj undo` reverts the commit so the selection can be redone.

## 7. Confirm

`jj status` and `jj show --git --no-pager @-`.
