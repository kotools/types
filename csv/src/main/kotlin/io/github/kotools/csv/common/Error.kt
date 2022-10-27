package io.github.kotools.csv.common

internal fun invalidConfigurationError(): Nothing =
    error("Given configuration is invalid")

internal fun unexpectedError(): Nothing =
    error("An unexpected error has occurred")
