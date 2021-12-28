package io.github.kotools.csv

import kotlin.reflect.full.primaryConstructor

internal inline infix fun <reified T : Any> Factory<T>.create(
    configuration: T.() -> Unit
): T = createOrNull(configuration) ?: error("Unable to create ${T::class}!")

internal inline infix fun <reified T : Any> Factory<T>.createOrNull(
    configuration: T.() -> Unit
): T? = try {
    T::class.primaryConstructor?.call()?.apply(configuration)
} catch (exception: Exception) {
    exception.printStackTrace()
    null
}

internal interface Factory<out T : Any>
