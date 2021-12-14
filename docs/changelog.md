# Kotools CSV - Changelog

All notable changes to this project will be documented in this file.

> The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0)
> and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Types of changes

- `Added` for new features.
- `Changed` for changes in existing functionality.
- `Deprecated` for soon-to-be removed features.
- `Removed` for now removed features.
- `Fixed` for any bug fixes.
- `Security` in case of vulnerabilities.

## Work in progress

### Added

- `csvReader {...}` and `csvReaderAsync {...}` for reading a CSV file or
  returning `null` if something goes
  wrong ([#5](https://github.com/kotools/csv-file/issues/5)).
- `strictCsvReader {...}` and `strictCsvReaderAsync {...}` for reading a CSV
  file or throwing an exception if something goes
  wrong ([#7](https://github.com/kotools/csv-file/issues/7)).
- `csvWriter {...}` and `csvWriterAsync {...}` for writing rows in a CSV file or
  returning `null` if something goes
  wrong ([#8](https://github.com/kotools/csv-file/issues/8)).
- `strictCsvWriter {...}` and `strictCsvWriterAsync {...}` for writing rows in a
  CSV file or throwing an exception if something goes
  wrong ([#14](https://github.com/kotools/csv-file/issues/14)).
