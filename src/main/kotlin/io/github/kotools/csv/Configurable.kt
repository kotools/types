package io.github.kotools.csv

internal fun Configurable.isInvalid(): Boolean = !isValid()

internal fun interface Configurable {
    fun isValid(): Boolean
}
