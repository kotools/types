# Kotools Types - Changelog

All notable changes to this project will be documented in this file.

> The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0)
> and this project adheres to
> [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Types of changes

- `Added` for new features.
- `Changed` for changes in existing functionality.
- `Deprecated` for soon-to-be removed features.
- `Removed` for now removed features.
- `Fixed` for any bug fixes.
- `Security` in case of vulnerabilities.

## Work in progress

### Added

- The `NotBlankString` type representing strings that contains at least one
  character, excluding whitespaces.
- The `AnyInt` hierarchy with the following types:
    - `NonZeroInt` representing integers other than zero.
    - `ZeroInt` representing the zero integer.
    - `PositiveInt` representing positive integers, including zero.
    - `NegativeInt` representing negative integers, including zero.
    - `StrictlyPositiveInt` representing strictly positive integers, excluding
      zero.
    - `StrictlyNegativeInt` representing strictly negative integers, excluding
      zero.
- The `NotEmptyList` type representing lists that contains at least one element.
- The `NotEmptySet` type representing sets that contains at least one element.
- The `NotEmptyMap` type representing maps that contains at least one pair of
  elements.
