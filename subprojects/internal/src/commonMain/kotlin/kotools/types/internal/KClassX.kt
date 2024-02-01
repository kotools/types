package kotools.types.internal

import kotlin.reflect.KClass

/** Returns the simple name of the type [T]. */
@InternalKotoolsTypesApi
public inline fun <reified T : Any> simpleNameOf(): String {
    val kClass: KClass<T> = T::class
    return kClass.simpleName
        ?: error("Getting simple name of '$kClass' shouldn't fail.")
}
