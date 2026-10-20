# Document examples

Samples of memos. The content is invented; only the shape, sentence endings, and orthography follow the user's style.

## Memo

A note to self or a future reader.

### `memo:` prefix

Optional, decided per note. A short memo fits on the prefix line; a longer one starts on the next line.

```
memo: 期待通り
```

```
memo:
このoptionってREADMEに書いてあったっけ?
```

### Verdict

A single word or 体言止め sentence.

```
期待通り
```

### Open question and later answer

Ends in 「〜か。」 or 「〜っけ?」; the answer may follow as its own memo.

```
このoptionってREADMEに書いてあったっけ?
```

```
あった。
```

### Verification record

```
念の為、macOSとLinux, どちらでも同じ出力になることを確認済み。
```

### Reasoning

Each `→` line is deduced from the one above, ending in the conclusion.

```
cacheのTTLは1時間、取得は10分ごと
→ 1時間のうち5回はcacheから返る
→ API呼び出しは6分の1になる
```

### Drill-down

A question, then what was found, then the next question it raises; afterthoughts in a trailing paren.

```
天気APIのアイコンはどこから取っているか?
→ レスポンスに相対パスで入っている
→ 自前でhostする必要があったっけ? (どちらかと言えばこれはAPI側のドキュメントの問題)
```

### Spec and investigation notes

Specs as bullets with 「(注釈)」; investigation as `Q. 〜? → A. 〜`.

```
API呼び出しのcacheについて
- 取得するたびにローカルに保存 (都市ごとに管理)
- Q. 1時間でexpireさせるのは何故? → A. 無料枠の呼び出し回数制限に収めるため
```

### Decision record

Ends in 「〜ことに。」「〜不要に。」.

```
話し合った上で、themeとその他の設定はファイルを分けることに。
そのため、`ThemeLoader` は不要に。
```
