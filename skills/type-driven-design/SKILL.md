---
name: type-driven-design
description: Design types so illegal states are unrepresentable. Use when designing domain types or value objects.
---

Build types whose illegal values and combinations the compiler rejects. Before guarding a design with runtime checks and contract comments, ask whether a type can guard it instead.

Two principles:

- **Make illegal states unrepresentable**: make impossible combinations impossible to construct
- **Parse, don't validate**: a value is validated once, when its type is constructed, and never re-validated

Name every type, field, and method with the `naming-convention` skill.

Examples by language:

- Java: [references/java/index.md](references/java/index.md)

# Check the information first

Before designing types, make sure you have these findings for every field involved:

- **Derived fields**: whether other fields determine it (derived, snapshot, or inconsistent), and by which formula
- **Nullability**: non-null or nullable
  - **Empty strings**: whether an empty string is a correct value or means absent
  - **Conditions for null and non-null**: co-occurrence (between fields, or with a discriminator value) and mutual exclusion
- **Value constraints**: strings, lists, numbers and dates, enumerations (fixed or data-driven), rules across fields, and which fields share a concept and its constraints

If any of it is missing, get it from the code or ask the user before designing. Do not design on guesses.

# Types for each finding

## Derived fields

- Derived from several fields → one value object holding the source fields, with the derived value as its method
- Snapshot → a field of its own

## Nullability

- Non-null → a plain field
- Nullable → the language's nullable or option marker
- "Nothing" in a collection → an empty collection, never an absent one

### Empty strings

- Correct value → keep the empty string
- Absent → the absent value. The type never holds an empty string for absence

### Conditions for null and non-null

- Co-occurrence
  - between fields → one value object holding them all, nullable as a whole
  - with a discriminator value → a sum type: one case per value, each carrying only its own fields
- Mutual exclusion → a sum type: one case per alternative
- Branch on a sum type with an exhaustive match and no catch-all branch

## Value constraints

- Strings, lists, numbers and dates → a value type whose only constructor checks the constraints, not a bare string or number
  - A violation at construction is the caller's bug: fail fast (an unchecked exception or a panic). Keep sensitive values out of error messages
  - If a caller treats a violation as a normal outcome, return a result type or give the type a predicate, instead of constructing and catching
  - Mask the string representation of sensitive values
  - A type holding a collection copies it defensively on construction, or takes a read-only collection type
- Enumerations
  - fixed → an enum that holds no codes
  - data-driven → an open enum that keeps its code: named constants for the values the code refers to, and any other code still valid
  - Branch on an enum with an exhaustive match and no catch-all branch. Once some members need values the others do not, make it a sum type
- Rules across fields → one value object holding the fields; its constructor enforces the rule
- The same concept with the same constraints in several fields → one value object type for all of them, even when each holds a single field. Fields whose constraints only happen to match get separate types

# Value objects

Beyond the findings above, also decide by:

- grouping fields into units that mean something together, so the unit can be reused
- how serious a mix-up would be: give a value its own type when assigning it to the wrong field would do serious harm
  - Example: an entity's identifier is a single field, but always gets its own type, since passing one entity's ID where another's is expected must not compile

# Booleans

- Hold the positive predicate
