---
name: write-like-yongju
description: Write Japanese text addressed to other people in the user's own voice — GitHub issue and PR bodies, review and issue comments, discussions, Slack messages, memos to self. Use whenever writing or rewriting Japanese conversation or documents the user will send or post as themselves, or when asked to write 私っぽく / 自分の口調で.
---

Apply `good-writing`. This skill adds the user's personal voice on top for anything written in Japanese to other people — issue and PR bodies, comments, discussions, Slack.

## Register follows the addressee

The split is by whether the text addresses someone, not by who ends up reading it. Read only the reference for the register you are writing in:

- Conversation — comments on issues, PRs, discussions, Slack messages: です・ます調. Read [references/conversation.md](references/conversation.md).
- Documents — issue and PR bodies, technical docs, ADRs, and memos to self or a future reader (such as a review comment on your own PR): だ・である調. Read [references/document.md](references/document.md).

## Orthography

The text is Japanese with Latin tokens embedded as ordinary words — so they are conjugated and particled directly, with no spacing and no translation: 「renameして」「nullableなら」「CIで確認」「7件」. Spacing is reserved for typographic objects, not word boundaries: a space before an opening paren and around inline code spans.

- Punctuation: 「、」「。」 for Japanese; half-width `?` `!` `:` `( )`. A sentence that ends on a Latin token may close with `.` instead of 「。」.
- Technical vocabulary stays in lowercase English (rename, mapping, fallback, nullable, cache, retry, timeout, fail-soft) — nouns as well as verbs: 「cacheが切れる」, not 「キャッシュが切れる」. English engineering slang and acronyms likewise stay as-is — LGTM, IMO, FWIW, YAGNI, AFAIK, nit, WIP — and sentence-initial ones take a comma: 「IMO, 〜」.
- `→` / `->` expresses renames, mappings and cause→effect inline: 「`foo` → `bar` にrename」「cacheが切れる → 再取得」.
- Lines starting with `→` carry a thought forward one step per line. Two uses:
  - Reasoning: each line is deduced from the one above, with no new facts, ending in the conclusion.
    ```
    無料枠は1日1000回
    → 5都市を10分ごとに取得すると1日720回
    → 枠内に収まる
    ```
  - Drill-down: from a question or observation, each line goes one step deeper — what was found, then the next finding or question it leads to.
    ```
    アイコンはどこから取っているか?
    → レスポンスに相対パスで入っている
    → 自前でhostする必要があったっけ?
    ```

## Never

- Full-width `？！（）：`, or a space at every Japanese/Latin boundary — that is machine-generated style.
