package io.github.kotools.csv.common

import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankString
import kotools.types.string.toNotBlankStringOrNull
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.starProjectedType

internal val <T : Any> KClass<T>.dataType: DataType<T>
    get() = DataType create this

private val KClass<*>.isInternal: Boolean
    get() = visibility?.isInternal ?: false

private val KVisibility.isInternal: Boolean
    get() = this == KVisibility.INTERNAL || this == KVisibility.PUBLIC

private infix fun String.toType(type: KType): Any = when (type) {
    Boolean::class.starProjectedType -> toBooleanStrict()
    Int::class.starProjectedType -> toInt()
    else -> this
}

@JvmInline
internal value class DataType<T : Any>
private constructor(private val type: KClass<T>) {
    val constructorParameters: List<NotBlankString>
        get() = type
            .primaryConstructor!!
            .parameters
            .mapNotNull { it.name?.toNotBlankStringOrNull() }

    private val properties: Map<NotBlankString, KProperty1<T, *>>
        get() = type
            .declaredMemberProperties
            .associateBy { it.name.toNotBlankString() }

    infix fun createType(record: Map<NotBlankString, String>): T {
        val arguments: Array<Any?> = record
            .map(::entryToType)
            .toTypedArray()
        return type.primaryConstructor!!.call(*arguments)
    }

    infix fun getValuesOf(item: T): List<String> =
        constructorParameters.mapNotNull {
            properties[it]
                ?.get(item)
                ?.toString()
        }

    private infix fun entryToType(entry: Map.Entry<NotBlankString, String>):
            Any? = properties[entry.key]
        ?.returnType
        ?.let(entry.value::toType)

    companion object {
        infix fun <T : Any> create(type: KClass<T>): DataType<T> {
            if (!type.isData) error("$type is not a data class")
            if (!type.isInternal) error("$type is not public or internal")
            return DataType(type)
        }
    }
}
