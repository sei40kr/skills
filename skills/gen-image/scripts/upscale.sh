#!/usr/bin/env bash
# Usage: upscale.sh <draft> <out-file> [size] [quality] [model]
#
# Re-renders <draft> through the pay-as-you-go OpenAI image edits API at the
# given size and quality, and writes the result to <out-file>. Every run is
# billed to OPENAI_API_KEY.
set -euo pipefail

draft=$1
out=$2
size=${3:-auto}
quality=${4:-high}
model=${5:-gpt-image-2.5-sunburst}
prompt='Upscale this image: raise its resolution, detail, and sharpness, and change nothing else.'

if [ "$size" != auto ]; then
  if ! [[ $size =~ ^([1-9][0-9]*)x([1-9][0-9]*)$ ]] || {
    w=${BASH_REMATCH[1]} h=${BASH_REMATCH[2]}
    ! ((w % 16 == 0 && h % 16 == 0 && w <= 3840 && h <= 3840 && w <= 3 * h && h <= 3 * w))
  }; then
    echo "Invalid size: $size (auto, or WxH with both sides multiples of 16, neither edge above 3840, and an aspect ratio between 1:3 and 3:1)" >&2
    exit 2
  fi
fi
case $quality in
low | medium | high | xhigh | max | auto) ;;
*)
  echo "Invalid quality: $quality (low, medium, high, xhigh, max, or auto)" >&2
  exit 2
  ;;
esac

: "${OPENAI_API_KEY:?OPENAI_API_KEY is not set}"
[ -f "$draft" ] || { echo "No such draft: $draft" >&2; exit 1; }

res=$(curl -sS https://api.openai.com/v1/images/edits \
  -H "Authorization: Bearer $OPENAI_API_KEY" \
  -F "model=$model" -F "image=@$draft" -F "prompt=$prompt" \
  -F "size=$size" -F "quality=$quality")
b64=$(jq -r '.data[0].b64_json // empty' <<<"$res")

if [ -z "$b64" ]; then
  echo "No image in the response:" >&2
  echo "$res" >&2
  exit 1
fi

mkdir -p "$(dirname "$out")"
base64 --decode <<<"$b64" >"$out"
echo "$out"
