---
name: gen-image
description: Generate an image through Codex, and upscale it with the paid OpenAI API when the user approves. Use whenever a task needs a raster image.
allowed-tools:
  - Bash(${CLAUDE_SKILL_DIR}/scripts/codex.sh *)
---

Generate a draft on the subscription, have the user review it, and spend money only when they approve. Script paths below are relative to this skill's directory. Write drafts and upscaled images to your scratchpad or another temporary directory, and copy only the accepted image to where the task needs it.

## 1. Generate the draft with the subscription

```sh
scripts/codex.sh <out.png> "<prompt>" [reference.png...]
```

It runs `codex exec` with Codex's built-in `image_gen` tool, which uses the user's ChatGPT Plus or Pro plan, and copies the result to `<out.png>`. If the Bash sandbox blocks Codex, rerun with the sandbox disabled.

Whenever you can prepare reference images for the result, pass them after the prompt; Codex attaches them to the prompt, and the prompt should say what to take from each. Codex sees nothing else, so put everything the image needs into the prompt and references. Look at the result. If it misses the request, revise the prompt and generate again before showing it to the user.

## 2. Have the user review the draft

Ask the questions in this step with `AskUserQuestion` when it's available.

Ask whether to open a preview of the draft, with the options Open and Skip. On Open, run `open` (macOS) or `xdg-open` (Linux) on it.

Decide whether the draft needs upscaling by comparing its resolution, which `file <draft.png>` prints, with the resolution the task needs. Then ask:

- If the draft is enough, offer Accept and Regenerate.
- If it needs upscaling, offer Upscale, stating the size, quality, and model you'd use, and Use the draft as is. Choosing Upscale is the user's approval to spend money; never upscale without it.

The user requests revisions through the free-text answer. On a revision, generate again from their feedback and ask again.

## 3. Upscale with the paid API

```sh
scripts/upscale.sh <draft.png> <out.png> [size] [quality] [model]
```

It sends the draft to the OpenAI image edits API, billed to `OPENAI_API_KEY`, with a fixed prompt asking to raise its resolution, detail, and sharpness and change nothing else. The API re-renders rather than upscaling pixel for pixel, so look at the result and compare it with the draft.

- `size`: `auto`, or `WxH` with both sides multiples of 16, neither edge above 3840, and an aspect ratio between 1:3 and 3:1. Defaults to `auto`.
- `quality`: `low`, `medium`, `high`, `xhigh`, `max`, `auto`. Defaults to `high`.
- `model`: defaults to `gpt-image-2.5-sunburst`.

If the API rejects a model name, check OpenAI's current model list rather than guessing.
