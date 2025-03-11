package kotools.types.internal

import org.kotools.types.internal.InternalKotoolsTypesApi
import kotlin.reflect.KClass

/** Returns the [simple name][KClass.simpleName] of the type [T]. */
@InternalKotoolsTypesApi
public inline fun <reified T : Any> simpleNameOf(): String =
    simpleNameOf(T::class)

/**
 * Returns the [simple name][KClass.simpleName] of the specified [type].
 */
@InternalKotoolsTypesApi
public fun <T : Any> simpleNameOf(type: KClass<T>): String =
    checkNotNull(type.simpleName) {
        "Getting simple name of '$type' shouldn't return 'null'."
    }
