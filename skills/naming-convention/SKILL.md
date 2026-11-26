---
name: naming-convention
description: Name a model's types, fields, and methods, and fix bad names. Use when naming, renaming, or reviewing names in a model, new or existing.
---

# Fix names

Check every name, whether new or taken from existing code, for each problem below, in this order: first names for a representation, then which words to use, then how the words are written. An earlier fix can remove the name before a later one applies.

- **A name for the representation instead of the concept**:
  - `…Flag` → a boolean named as a predicate
  - `…Code`, `…Kbn`, `…Kubun`, `…Div`, `…Cls` → the name of the concept
  - `…Label`, `disp…`, `…Text`, `…Fmt` → the field the display is built from
  - A data type in the name, as a suffix or as a Hungarian-notation prefix (`…Str`, `…Num`, `…Int`, `…List`, `…Map`, `str…`, `int…`, `b…`, `lst…`) → drop it; the type says it
  - A filler word that adds nothing (`…Data`, `…Info`, `…Value`) → drop it. The single component of a value type may still be named `value`
- **Romanized Japanese** → the English word for the concept
  - Exception: a proper noun, such as a service or product name, whose official spelling is romanized. Keep it as officially spelled
  - When unsure whether a word is such a proper noun, ask the user
- **Japanese-made English** → the term English speakers use for the concept
- **Correct but unidiomatic English** → the phrasing English speakers usually use
- **Abbreviation** → the full words, unless the abbreviation is itself the usual term in English
- **Wrong word boundary**, such as two words written as one → split or join the words as English writes them, and set the case boundaries accordingly
- **Typo** → the correct spelling

# Ubiquitous language

- Every name that mentions a concept uses the same word for it, in types, fields, methods, and parameters alike
- Different concepts never share a name
  - Do not tell them apart by adding a word to one name; it reads as part of the other. Rename both
- When two names compete for one concept and the code does not decide between them, ask the user

# Method names

- Changes the model itself → a verb in the active voice
- Returns a copy with the change applied, leaving the original unchanged → the passive form (past participle)
