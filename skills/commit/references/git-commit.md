# Committing with git

Commands for each step of the `commit` skill in a git repository.

## 1. Survey

`git status` (never `-uall`; it can exhaust memory on large repositories), `git diff`, `git diff --staged`, and `git log` for the repository's message style. `git-surgeon hunks` lists every unstaged hunk with its ID (`--staged` for staged ones, `--file <path>` to narrow, `--full` for line numbers).

## 3. Fold into unpushed commits

`git log @{upstream}..` lists the unpushed commits. To fold into one, stage the related hunks as in step 4, then:

```
git-surgeon amend <commit>
git-surgeon reword <commit> -m "<revised full message>"
```

`amend` folds the staged changes into `<commit>`; `reword` revises its message. To merge a range of unpushed commits instead, `git-surgeon squash <oldest-commit> -m "<message>"` combines `<oldest-commit>..HEAD` into one. These rewrite and re-sign commits, so the sandbox rule from step 6 applies to them too.

## 4. Select hunks and verify

Stage by hunk ID from `git-surgeon hunks`:

```
git-surgeon stage <hunk-id>...
git-surgeon stage <hunk-id> --lines 5-30
git-surgeon unstage <hunk-id>...
```

`--lines` takes a hunk-relative range, narrowing a hunk that mixes related and unrelated lines; `--full` shows the numbers. `git add .` / `git add -A` are denied by the harness, and plain `git add <file>` is too coarse for a file with mixed hunks.

`git diff --staged` is the diff that will land; check it against the user's intent.

## 6. Commit

`git commit -m "<message>"`, or the amend variant when folding. Pass a multi-line message through a heredoc so its formatting survives the shell:

```
git commit -m "$(cat <<'EOF'
<message>
EOF
)"
```

A failed pre-commit hook means the commit did not happen. Fix the issue, re-stage, and retry the same command — do not switch to `--amend`, or you will rewrite the previous commit.

## 7. Confirm

`git status`.
