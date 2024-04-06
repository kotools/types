package org.kotools.types.kotlinx.serialization

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.reflect.KClass

/**
 * Contains modules for serializing the types provided by Kotools Types using
 * the kotlinx.serialization library.
 */
@ExperimentalKotoolsTypesApi
public object KotoolsTypesSerializers {
    /**
     * Returns the string representation of this object.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: KotoolsTypesSerializersKotlinSample.toString_override.md
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String {
        val type: KClass<out KotoolsTypesSerializers> = this::class
        return checkNotNull(type.simpleName) {
            "Getting simple name of '$type' shouldn't return 'null'."
        }
    }
}
