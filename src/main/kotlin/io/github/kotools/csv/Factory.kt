package io.github.kotools.csv

import kotlin.reflect.full.primaryConstructor

internal inline infix fun <reified T : Any> Factory<T>.create(
    configuration: T.() -> Unit
): T {
    val instance: T = T::class.primaryConstructor!!.call()
    instance.configuration()
    return instance
}

internal interface Factory<out T : Any>
