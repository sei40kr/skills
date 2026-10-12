---
name: grilling
description: Interview the user about a plan, decision, or idea until nothing is left assumed. Use when the user wants their thinking stress-tested or says "grill me".
---

Interview the user until the two of you share an understanding of the subject.

## The design tree

Model the subject as a design tree: each decision branches into the decisions that hang off it. The **frontier** is every decision whose prerequisites are settled, so it can be asked now without guessing at answers you haven't heard. A **round** is one frontier, asked in full and answered in full. A question that depends on another question still open in this round belongs to a later round.

Answers settle decisions and push the frontier outward. Recompute it after each round and ask the next.

## Asking a round

Every question comes with your recommended answer. When your recommendation argues against the question as worded, reword the question so the recommendation answers it.

### When the `AskUserQuestion` tool is available (Claude Code)

Ask through the tool, up to four questions per call. A larger frontier takes several calls back to back, with no recomputing between them.

- `question` is complete on its own, ends in a question mark, and carries the context needed to answer it.
- `header` names the decision in at most 12 characters.
- `options` are two to four concrete answers, each with a `description` of what choosing it implies. Your recommendation goes first, with "(Recommended)" after its label. The UI adds "Other" for free text, so an open-ended question still lists the candidates you would weigh.
- `multiSelect: true` when the answers are not mutually exclusive.
- `preview` when the options are artifacts to compare: mockups, snippets, configuration.

An "Other" answer is the user's answer, not a rejection of the question.

### Elsewhere

Ask in prose, numbered so the user can answer by number, then wait for the answers:

```
❓ **Q1** - **<title>**: <question, with any choices>

➡️ <your recommended answer>

---

❓ **Q2** - **<title>**: <question, with any choices>

➡️ <your recommended answer>
```

## Facts are yours, decisions are the user's

When a question needs a fact from the environment (files, tools, docs), look it up or dispatch a sub-agent; never ask the user for what you could find out. Don't block on it: only the questions downstream of a running exploration wait, so ask the rest of the frontier now.

Never settle a decision yourself. A session in which you answered your own questions has failed, not finished.

## Ending

The session is done when the frontier is empty. Summarize the settled decisions and ask whether the understanding is shared, the same way you asked the rounds. Do not act, write a plan, or start work until the user confirms.

If the user asks for one question at a time, ask one per round. If they ask to wrap up, stop and summarize where things stand.
