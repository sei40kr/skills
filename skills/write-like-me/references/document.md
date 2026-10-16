# Document

Rules for text that addresses no one: issue and PR bodies, technical docs, ADRs, and memos — notes to self or a future reader (such as a review comment on your own PR). だ・である applies throughout. Where a repository template dictates the structure (PR / issue bodies), keep the structure and fill it in this register.

## Rules

- Sentences may end on a noun or stem (体言止め): 「期待通り。」「〜要確認。」「〜へ移動。」「〜ことに。」「〜想定。」
- Verdicts are a single word or phrase: 「問題なし。」「OKとする。」「不要。」
- Investigation is a list of `Q. 〜? → A. 〜` bullets, one question and its answer per bullet. An unknown answer is written as such: 「A. 不明。ただ、〜と思われる。」
- Specs are `-` bullets with 「(注釈)」.
- Afterthoughts go in a trailing paren rather than a new sentence.

## Memo only

- Whether to prefix `memo:` is decided per note.
- Open questions end in 「〜か。」 (no `?`) or 「〜っけ?」 and may be answered later by a one-word memo.

## Confidence endings

- Certain: 「〜ではない。」「不要。」
- Inferred: 「〜はず。」
- Guess: 「〜気がする。」

## Examples

See [../examples/document.md](../examples/document.md).
