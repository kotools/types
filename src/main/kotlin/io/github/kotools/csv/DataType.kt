package io.github.kotools.csv

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.starProjectedType

internal fun <T : Any> KClass<T>.toDataTypeOrNull(): DataType<T>? =
    DataType createOrNull this

private infix fun <T : Any> Map<String, String>.getOrNull(
    property: KProperty1<T, *>
): Any? = get(property.name)?.let {
    when (property.returnType) {
        Boolean::class.starProjectedType -> it.toBooleanStrict()
        Int::class.starProjectedType -> it.toInt()
        else -> it
    }
}

@JvmInline
internal value class DataType<T : Any>
private constructor(private val value: KClass<T>) {
    internal infix fun createTypeOrNullFrom(record: Map<String, String>): T? =
        try {
            val arguments: Array<Any?> = value.declaredMemberProperties
                .map { record getOrNull it }
                .toTypedArray()
            value.primaryConstructor?.call(*arguments)
        } catch (error: Exception) {
            error.printStackTrace()
            null
        }

    companion object {
        infix fun <T : Any> createOrNull(type: KClass<T>): DataType<T>? =
            if (!type.isData || type.visibility?.isNotInternal() == true) null
            else DataType(type)

        private fun KVisibility.isNotInternal(): Boolean =
            this != KVisibility.INTERNAL && this != KVisibility.PUBLIC
    }
}
