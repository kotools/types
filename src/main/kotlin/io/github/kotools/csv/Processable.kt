package io.github.kotools.csv

internal fun <R : Any> Processable<R>.processOrNull(): R? = try {
    process()
} catch (exception: CsvConfigurationException) {
    null
}

internal fun interface Processable<out R : Any> {
    fun process(): R
}
