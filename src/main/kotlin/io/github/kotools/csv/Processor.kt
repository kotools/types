package io.github.kotools.csv

internal inline fun <T : Any> processOrNull(block: () -> Processor<T>?): T? =
    block()
        ?.processOrNull()

internal sealed interface Processor<out T : Any> {
    fun process(): T
    fun processOrNull(): T?
}
