---
name: inspect-constraints
description: Inspect a model for the constraints on each field: nullability and when it holds, and value constraints including code values. Use when asked what values a model's fields accept.
arguments: [model]
argument-hint: "[model]"
---

- Input: the target model `$model`. If it is empty, ask the user. Find the location of its source code in the project's CLAUDE.md or docs, or ask the user. Treat the source code as read-only, and trust it over design docs
- Output: findings in the conversation, each with its source `path:line`, plus carry-over items for whatever the code does not settle

# 1. Find which fields can be null

Decide each field's nullability from evidence. Record each piece with its location and certainty. A path is a place that sets the field; other paths may always exist.

| Evidence | Supports | Certainty |
|---|---|---|
| The field's own declaration: its type, an option type, or a nullable / non-null annotation | as shown | High |
| Whether the DB column backing the value is `NOT NULL` | as shown | High if `NOT NULL`; Low otherwise, since accepting null does not show a null is ever written |
| A path assigns `null` or leaves the field unset | nullable | High |
| A path assigns a value, or a validation on it rejects missing input | non-null | Medium; other paths may exist |
| Code that reads the field checks for null, or dereferences without a check | as shown | Low; it may only reflect that code |
| A design doc or code comment | as shown | Low; often stale |
| The field's name | as shown | None |

Classify each field from its evidence:

- High evidence settles it: `non-null` or `nullable`, as the evidence shows
- Without High evidence, it is `unknown`, a carry-over item. Record which way the evidence leans and its certainty (Medium or Low)
- High evidence on both sides is a contradiction: `unknown`

## Empty strings

Code often uses `""` and `null` interchangeably. For every string field, find whether it can be `""`, and if so, what `""` means there:

- **Correct value**: `""` is a value of the field, distinct from having no value
- **Absent**: `""` says the attribute does not exist for this instance. Count it as null for the classification above

1. Hypothesize what `""` means from the field's meaning
2. Verify it on the paths that set the field
3. If they do not settle it, it is a carry-over item

## Conditions for null and non-null

A bare `nullable` is not a spec. For each nullable field, find the conditions under which it is null and under which it is non-null. In particular, always investigate:

- **Co-occurrence**: a condition under which a field is always non-null
  - Between fields: "if A is non-null, B is always non-null" (and whether it holds both ways)
  - With a discriminator value: "when `status` is X, A is non-null"
- **Mutual exclusion**: "if A is null, B is always non-null", or "exactly one of A and B is non-null"

Work hypothesis-first:

1. From the meaning of the fields, write down candidate conditions as hypotheses before reading the implementation. Sources of meaning:
   - field names
   - screen labels
   - business context
2. While reading the code, add a hypothesis wherever several fields are set or cleared together in the same branch
3. Verify each against the code: find the paths that set the fields and the code that branches on them. Look for a counterexample path as hard as for a confirming one.
4. Give each hypothesis a verdict:
   - **Confirmed**: every path that sets the fields upholds it. Cite the locations
   - **Refuted**: a path violates it. Cite the counterexample
   - **Undetermined**: the code neither confirms nor refutes it, including when the value comes from code or data you cannot read, or the paths are too dynamic to follow. Do not decide it yourself; it is a carry-over item

# 2. Collect value constraints

For every input and every value that crosses a boundary, record:

- **String**:
  - min / max length, and whether it is counted in characters or bytes, and in which charset
  - allowed character set
  - forbidden characters
  - trimming or normalization applied before the check
- **List / repeated items**: min / max number of elements, whether duplicates are allowed, whether order matters
- **Numbers and dates**: range, digits, format
- **Codes** (type, status, kind, flag): the set of values. Do not infer it from the values you happen to see in one usage
  - each value and its meaning
  - where the set is defined:
    - **Fixed**: constants or literals in code. The set changes only with a code change
    - **Data-driven**: DB master table, properties, external configuration. The set can grow or shrink without a code change
  - how a value outside the set is handled
  - whether the same code means something else elsewhere
- **Across fields**: a rule that involves several fields

Collect from **every** place that checks the value. When they disagree, record every one with its location; the strictest one that actually rejects input is the effective rule. Do not merge them silently. If the code does not settle where a code's set is defined, it is a carry-over item.

# Carry-over items

For each, record what you checked (with source `path:line`), why the code could not decide it, and who or what could. Do not decide it yourself.
