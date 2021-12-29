package io.github.kotools.csv

internal sealed interface Configurable {
    fun isValid(): Boolean
}
