package kotools.types.internal

import kotlin.jvm.JvmSynthetic
import kotlin.reflect.KClass

@JvmSynthetic
internal inline fun <reified T : Any> unexpectedCreationError(
    value: Any
): Nothing {
    val kClass: KClass<T> = T::class
    val typeName: String = kClass.simpleName
        ?: error("Getting simple name of '$kClass' shouldn't return 'null'.")
    error("Creating a $typeName from '$value' shouldn't fail.")
}
