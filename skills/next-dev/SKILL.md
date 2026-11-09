---
name: next-dev
description: Verify Next.js changes against the user's running dev server instead of starting your own. Use whenever checking that an edit works in a Next.js project — curling a route, browser checks, or `next build` — and especially before running `next dev` or guessing a localhost port.
---

Verify a change in a Next.js project without disturbing the dev server the user already has running.

## Check the Next.js version

Do this before either check below. Run `next --version` via the project's package runner. If it is older than 16.2, stop and tell the user this skill requires Next.js 16.2 or later.

## Visual / behavioral check

Never start your own `next dev`, and never kill or restart the user's. Edits hot-reload, so use their server.

Get the server's URL from the dev lock file, then confirm it responds:

```sh
APP_URL=$(jq -r .appUrl .next/dev/lock)   # e.g. http://localhost:3000
curl -s "$APP_URL/<the-route-you-changed>" | head
```

If the lock file is missing or nothing is listening, stop — tell the user and let them start it. A 500 means the code is broken, not the server: read the error and fix your change.

## Build verification

For errors that only show at build time (`generateStaticParams`, `dynamicParams`, non-ASCII route directories), run `next build` via the project's package runner in the repo root, even while the dev server is running.

`next build` rewrites `next-env.d.ts` to import `.next/types/...` instead of `.next/dev/types/...`. If the file is tracked, leave that change out of commits.
