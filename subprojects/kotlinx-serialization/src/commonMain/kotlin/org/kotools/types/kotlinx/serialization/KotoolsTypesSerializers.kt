package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.reflect.KClass

/**
 * Contains modules for serializing the types provided by Kotools Types using
 * the kotlinx.serialization library.
 */
@ExperimentalKotoolsTypesApi
public object KotoolsTypesSerializers {
    /**
     * Returns the module for serializing all types.
     *
     * Here's an example of calling this property from Kotlin code:
     *
     * SAMPLE: KotoolsTypesSerializersKotlinSample.all.md
     */
    public val all: SerializersModule
        get() = SerializersModule { include(zero) }

    /**
     * Returns the module for serializing the [Zero] type.
     *
     * Here's an example of calling this property from Kotlin code:
     *
     * SAMPLE: KotoolsTypesSerializersKotlinSample.zero.md
     */
    public val zero: SerializersModule
        get() = SerializersModule { contextual(ZeroAsByteSerializer) }

    /**
     * Returns the string representation of this object.
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: KotoolsTypesSerializersKotlinSample.toString_override.md
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String {
        val type: KClass<out KotoolsTypesSerializers> = this::class
        return checkNotNull(type.simpleName) {
            "Getting simple name of '$type' shouldn't return 'null'."
        }
    }
}
