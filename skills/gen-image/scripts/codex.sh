#!/usr/bin/env bash
# Usage: codex.sh <out-file> <prompt>
#
# Generates an image with Codex's built-in image_gen tool under the user's
# ChatGPT subscription and copies it to <out-file>. Exits 1 when Codex saved
# no image.
set -euo pipefail

out=$1
prompt=$2
images=${CODEX_HOME:-$HOME/.codex}/generated_images

if ! command -v codex >/dev/null; then
  echo "Codex CLI is not installed. Tell the user; don't generate the image any other way." >&2
  exit 1
fi
if ! codex login status 2>&1 | grep -Eq 'Logged in using (ChatGPT|personal access token)'; then
  echo "Codex is not logged in with a ChatGPT plan. Ask the user to run \`codex login\`; don't generate the image any other way." >&2
  exit 1
fi

workdir=$(mktemp -d)
trap 'rm -rf "$workdir"' EXIT
events=$workdir/events.jsonl

# Codex writes the image under $CODEX_HOME itself, so its own sandbox can stay read-only.
if ! codex exec --json --skip-git-repo-check -C "$workdir" --sandbox read-only \
  "Generate one image with the image_gen tool from this prompt, and do nothing else: $prompt" >"$events"; then
  cat "$events" >&2
  exit 1
fi

# Codex saves images under generated_images/<thread_id>/, so other sessions'
# images generated meanwhile can't be picked up by mistake.
thread=$(jq -rR 'fromjson? | select(.type == "thread.started") | .thread_id' "$events" | head -n 1)
image=
if [ -n "$thread" ]; then
  image=$(find "$images/$thread" -type f -print0 2>/dev/null |
    xargs -0 ls -t 2>/dev/null | head -n 1 || true)
fi
if [ -z "$image" ]; then
  echo "Codex saved no image; its image generation needs a ChatGPT Plus or Pro plan. Tell the user; don't generate the image any other way." >&2
  exit 1
fi

mkdir -p "$(dirname "$out")"
cp "$image" "$out"
echo "$out"
