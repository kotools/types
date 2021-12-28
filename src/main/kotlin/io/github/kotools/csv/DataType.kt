package io.github.kotools.csv

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.starProjectedType

@Throws(CsvException::class)
internal fun <T : Any> KClass<T>.toDataType(): DataType<T> =
    DataType create this

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
private constructor(private val value: KClass<T>) {
    @Throws(CsvException::class)
    internal infix fun createType(record: Map<String, String>): T =
        createTypeOrNull(record) ?: invalidTypeException(value)

    internal infix fun createTypeOrNull(record: Map<String, String>): T? = try {
        val arguments: Array<Any?> = value.declaredMemberProperties
            .map { record[it.name]?.toType(it.returnType) }
            .toTypedArray()
        value.primaryConstructor?.call(*arguments)
    } catch (exception: Exception) {
        exception.printStackTrace()
        null
    }

    companion object {
        @Throws(CsvException::class)
        infix fun <T : Any> create(type: KClass<T>): DataType<T> =
            createOrNull(type) ?: invalidTypeException(type)

        infix fun <T : Any> createOrNull(type: KClass<T>): DataType<T>? = type
            .takeIf { type.isData && type.isInternal() }
            ?.let(::DataType)
    }
}
