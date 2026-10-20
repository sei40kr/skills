# Conversation

Rules for comments on issues, PRs, and discussions, and Slack messages — anything addressed to other people.

## Politeness

Every sentence ends in です・ます, observations and requests included: 「〜はずです。」「〜ではありません。」「〜してください。」. Plain form (常体) in a comment to someone is a slip, not a style. Only list items may end on a noun.

Politeness lives in the sentence ending, not in extra words. Directives are softened grammatically — 「〜しましょう」「〜しましょうか」「〜お願いします」 — never with keigo padding (「〜させていただきます」) or pleasantries. Thanks and apology are one clause, optionally with 🙇.

Emoji: 🙇 for apology or thanks, 👍 for ack. Nothing else.

## Structure

- Reasons attach to the verdict's own sentence with 「〜ので」「〜ため」, or follow as a separate 「なぜなら〜からです。」 when the chain is longer than a clause.
- Mark scope before content when the comment is outside the PR's purpose or optional: 「PRのscope外ですが、」「(対応不要)」「(修正任意)」. Readers decide what to act on from the first few characters.
- Ask before judging. When the author may have had a reason, the comment is a question about intent (「あえて〜しなかった理由はありますか?」「〜という意図がありますか?」), not a verdict.
- Replying to several points: quote each with `>` and answer directly under it.
- `---` marks that the paragraph below changes topic from the one above. Use it only when nothing else (a `>` quote, a label) already shows the boundary.
- Follow-ups are labelled lines — `補足:` `結論:` `参考:` `提案として、` — so a reader can see the comment's role before reading it.
- A fix report is the commit hash plus what changed, nothing more: 「3895f8f で対応しました。」 or 「修正しました: 3895f8f」
- Approval is 「LGTM」 optionally plus one clause of thanks.

## Confidence endings

- Certain: 「〜です。」「〜ではありません。」「不要です。」
- Inferred: 「〜はずです。」「〜と思います。」
- Guess: 「〜気がします。」「〜かもしれませんね。」「〜そうですね。」

## Owning mistakes

When the writer was wrong or late, say so first and in one clause — 「すみません、」「自分の記憶違いでした。」「〜しておくべきでした。」 — then move straight to the correction. No elaboration of the mistake.

## Never

- Headings inside a comment.

## Examples

See [../examples/conversation.md](../examples/conversation.md).
