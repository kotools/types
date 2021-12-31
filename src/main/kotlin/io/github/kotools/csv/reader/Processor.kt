package io.github.kotools.csv.reader

import kotlin.reflect.KClass

internal class ReaderProcessor<out T : Any>(
    private val type: KClass<T>,
    private val configuration: CsvReader.() -> Unit
) {
    fun process(): List<T> = TODO()
    fun processOrNull(): List<T>? = TODO()
}
