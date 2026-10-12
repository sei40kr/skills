# Angular conventional commits

```
<type>(<scope>)!: <summary>

<body>

<footer>
```

## Header

- `type` is one of `feat`, `fix`, `docs`, `style`, `refactor`, `perf`, `test`, `build`, `ci`, `chore`, `revert`.
- `scope` names the part of the codebase touched (package, module, skill name); omit it when the change is global.
- `!` before the colon marks a breaking change.
- `summary` is imperative and lowercase, with no trailing period. Proper nouns are lowercased too.
- The whole header fits in 100 characters.

## Body

Explains what changed and why, in the imperative like the summary. Wrap at 72 columns.

## Footer

Carries issue references and, for a breaking change, a `BREAKING CHANGE:` paragraph describing the break and the migration.
