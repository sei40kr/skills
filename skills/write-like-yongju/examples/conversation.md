# Conversation examples

Samples of comments and messages addressed to other people. The content is invented; only the shape, sentence endings, and orthography follow the user's style.

## Approve

LGTM with at most one clause of thanks.

```
ご対応ありがとうございます。LGTMです。
```

## One-line review

A directive softened by 「〜しましょう」; afterthoughts in a trailing paren.

```
何をparseするのか分かるように、`parseFrontmatter` にrenameしましょうか。(テスト名も同様に)
```

## Review with a reason

The reason rides on the same sentence, or follows as 「なぜなら〜からです。」 when it is long.

```
`width` と `height` のどちらか一方だけundefined, という状態が作れてしまうため、`Size` 型にまとめて両方必須にした方が良いです。
```

```
文字列ではなくunion typeで持つことを推奨します。
なぜなら typo した値を渡してもコンパイルが通ってしまい、実行するまで気づけないからです。
```

## Scoped aside

The scope marker comes first.

```
(対応不要) すごいnitpicksなのですが、colourではなくcolorに統一されているのでそちらに合わせると良さそうですね。
```

## Ask rationale before judging

```
ここは `date-fns` を使う想定だったのですが、自前で日付計算を実装した理由はありますか?
```

## Request

```
空文字の場合のテストも追加してください。
```

## Reporting a fix

Commit hash plus what changed, nothing more.

```
修正しました: a1b2c3d
```

```
c7d8e9f で対応しました。
- icon は `assets/icons/` へ配置
- 既存の `public/` 配下は rename / move していません
```

## Correcting yourself

Own the mistake in one clause, then the correction.

```
すみません、Windowsだと区切り文字が違うので `path.sep` の考慮は必要でした。
ただ、`path.join` を通していれば十分なはずで、ここは手で分岐しなくて良いと思います。
```

## Answering several points

Quote each point with `>` and answer beneath it.

```
> 1. 今までは、形式が不正でも動いていたということですか？

Yes. 手で編集された設定ファイルには不正な値が入っているかもしれない、という主張です。

> 2. 命名 (`APP_` prefix なし) でよいか

はい、不要です。
```

## Changing topic

`---` separates a paragraph on a different topic when nothing else marks the boundary.

```
`--verbose` の出力はstderrに寄せましょう。stdoutはpipeで渡される想定なので、混ざると後段のparseが壊れます。

---

ついでですが、READMEのinstall手順が古いままなので、別PRで更新しておきます。
```

## Naming nitpicks

```
unparsableという英単語はないはずです。unparseableですね。
```

## Status

```
調査中です。お待ちください。
```
