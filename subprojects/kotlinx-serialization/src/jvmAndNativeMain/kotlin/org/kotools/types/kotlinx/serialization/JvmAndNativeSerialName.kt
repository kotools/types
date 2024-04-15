package org.kotools.types.kotlinx.serialization

import kotlin.reflect.KClass

internal actual inline fun <reified T : Any> serialNameOf(): String {
    val type: KClass<T> = T::class
    return checkNotNull(type.qualifiedName) {
        "Getting qualified name of $type shouldn't return 'null'."
    }
}
