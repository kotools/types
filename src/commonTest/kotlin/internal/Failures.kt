package kotools.types.internal

import kotlin.reflect.KClass
import kotlin.test.fail

internal inline fun <reified T : Any> unexpectedCreationFailure(
    value: Any
): Nothing {
    val kClass: KClass<T> = T::class
    val typeName: String = kClass.simpleName
        ?: error("Getting simple name of '$kClass' shouldn't return 'null'.")
    fail("Creating a $typeName from '$value' shouldn't fail.")
}
