package kotools.types.internal

import kotlin.reflect.KClass

/** Returns the simple name of the type [T]. */
@InternalKotoolsTypesApi
public inline fun <reified T : Any> simpleNameOf(): String {
    val type: KClass<T> = T::class
    return checkNotNull(type.simpleName) {
        "Getting simple name of '$type' shouldn't return 'null'."
    }
}
