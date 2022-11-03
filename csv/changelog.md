# Kotools CSV - Changelog

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

## Version 2.2.0 - 2022/03/23

### Added

Integrate [Kotools Types](https://github.com/kotools/types) library
([#42](https://github.com/kotools/csv/issues/42)).

## Version 2.1.1 - 2022/02/10

### Fixed

Fix pagination and filter ordering in the reader.

## Version 2.1.0 - 2022/02/02

### Added

- Paginated CSV file's reading as a given type
  ([#13](https://github.com/kotools/csv/issues/13)).
- Filtered CSV file's reading as a given type
  ([#35](https://github.com/kotools/csv/issues/35)).

## Version 2.0.2 - 2022/11/03

### Fixed

Add missing `csvWriterAsync` function
([#6](https://github.com/kotools/libraries/issues/6)).

## Version 2.0.1 - 2022/02/01

### Fixed

File targeting on Windows
([#37](https://github.com/kotools/csv/issues/37)).

## Version 2.0.0 - 2022/01/05

### Added

- CSV file's reading as a given
  type ([#9](https://github.com/kotools/csv/issues/9)).
- CSV file's writing as a given
  type ([#22](https://github.com/kotools/csv/issues/22)).

### Changed

Methods design for explicit error handling
([#24](https://github.com/kotools/csv/issues/24)).

### Removed

Untyped functions ([#26](https://github.com/kotools/csv/issues/26)).

## Version 1.0.0 - 2021/12/16

### Added

- `csvReader {...}` and `csvReaderAsync {...}` for reading a CSV file or
  returning `null` if something goes
  wrong ([#5](https://github.com/kotools/csv/issues/5)).
- `strictCsvReader {...}` and `strictCsvReaderAsync {...}` for reading a CSV
  file or throwing an exception if something goes
  wrong ([#7](https://github.com/kotools/csv/issues/7)).
- `csvWriter {...}` and `csvWriterAsync {...}` for writing rows in a CSV file or
  returning `null` if something goes
  wrong ([#8](https://github.com/kotools/csv/issues/8)).
- `strictCsvWriter {...}` and `strictCsvWriterAsync {...}` for writing rows in a
  CSV file or throwing an exception if something goes
  wrong ([#14](https://github.com/kotools/csv/issues/14)).
