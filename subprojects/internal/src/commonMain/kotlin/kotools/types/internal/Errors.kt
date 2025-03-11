package kotools.types.internal

import kotlinx.serialization.SerializationException
import org.kotools.types.internal.InternalKotoolsTypesApi
import kotlin.reflect.KClass

/** Throws a `SerializationException` with the specified [message]. */
@InternalKotoolsTypesApi
public fun serializationError(message: ErrorMessage): Nothing =
    throw SerializationException("$message")

/**
 * Throws a `SerializationException` with the specified [message], or throws an
 * [IllegalArgumentException] if the [message] is [blank][String.isBlank].
 */
@InternalKotoolsTypesApi
public fun serializationError(message: String): Nothing {
    val isValid: Boolean = message.isNotBlank()
    require(isValid) { "Message shouldn't be blank" }
    throw SerializationException(message)
}

/**
 * Throws a `SerializationException` indicating that it was unable to
 * deserialize the type [T] from the specified [value].
 */
@InternalKotoolsTypesApi
public inline fun <reified T : Any> deserializationError(value: Any): Nothing {
    val message: ErrorMessage = deserializationErrorMessage<T>(value)
    serializationError(message)
}

/**
 * Throws an [IllegalStateException] indicating that creating an instance of
 * type [T] with the specified [value] shouldn't fail.
 */
@InternalKotoolsTypesApi
public inline fun <reified T : Any> unexpectedCreationError(
    value: Any
): Nothing {
    val kClass: KClass<T> = T::class
    val typeName: String = kClass.simpleName
        ?: error("Getting simple name of '$kClass' shouldn't return 'null'.")
    error("Creating an instance of $typeName with '$value' shouldn't fail.")
}
