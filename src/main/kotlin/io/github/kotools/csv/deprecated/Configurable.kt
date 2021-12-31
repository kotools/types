package io.github.kotools.csv.deprecated

internal sealed interface Configurable {
    fun isValid(): Boolean
}
