package io.github.kotools.csv

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.starProjectedType

internal infix fun <T : Any> Map<String, String>.toTypeOrNull(type: KClass<T>):
        T? = if (!type.isData) null else try {
    val arguments: Array<Any?> = type.declaredMemberProperties
        .map { it getValueFrom this }
        .toTypedArray()
    type.primaryConstructor?.call(*arguments)
} catch (error: Exception) {
    null
}

private infix fun <T : Any> KProperty1<T, *>.getValueFrom(
    map: Map<String, String>
): Any? = map[name]?.let {
    when (returnType) {
        Boolean::class.starProjectedType -> it.toBooleanStrict()
        Int::class.starProjectedType -> it.toInt()
        else -> it
    }
}
