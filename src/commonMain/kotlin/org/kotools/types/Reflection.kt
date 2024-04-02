package org.kotools.types

import kotlin.jvm.JvmSynthetic
import kotlin.reflect.KClass

@JvmSynthetic
internal inline fun <reified T : Any> qualifiedNameOf(
    within: KotoolsTypesPackage = KotoolsTypesPackage.ROOT
): String {
    val kClass: KClass<T> = T::class
    val type: String = kClass.simpleName
        ?: error("Getting simple name of '$kClass' shouldn't fail.")
    return "${within}.$type"
}
