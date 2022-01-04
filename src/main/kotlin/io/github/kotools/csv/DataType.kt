package io.github.kotools.csv

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.starProjectedType

internal fun <T : Any> KClass<T>.toDataTypeOrNull(): DataType<T>? =
    DataType createOrNull this

private fun <T : Any> KClass<T>.isInternal(): Boolean =
    visibility?.isInternal() ?: false

private fun KVisibility.isInternal(): Boolean =
    this == KVisibility.INTERNAL || this == KVisibility.PUBLIC

private infix fun String.toType(type: KType): Any = when (type) {
    Boolean::class.starProjectedType -> toBooleanStrict()
    Int::class.starProjectedType -> toInt()
    else -> this
}

@JvmInline
internal value class DataType<out T : Any>
private constructor(private val type: KClass<T>) {
    infix fun createTypeOrNull(record: Map<String, String>): T? = try {
        val properties: Map<String, KProperty1<T, *>> =
            type.declaredMemberProperties.associateBy(KProperty1<T, *>::name)
        val arguments: Array<Any?> = record
            .map { properties[it.key]?.returnType?.let(it.value::toType) }
            .toTypedArray()
        type.primaryConstructor?.call(*arguments)
    } catch (exception: Exception) {
        null
    }

    companion object {
        infix fun <T : Any> createOrNull(type: KClass<T>): DataType<T>? = type
            .takeIf { it.isData && it.isInternal() }
            ?.let(::DataType)
    }
}
