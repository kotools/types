package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.simpleNameOf
import org.kotools.types.Zero
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion

/**
 * Contains modules for serializing the types provided by Kotools Types using
 * the kotlinx.serialization library.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public object KotoolsTypesSerializers {
    /**
     * Returns the module for serializing all types.
     *
     * Here's an example of calling this property from Kotlin code:
     *
     * ```kotlin
     * val format = Json { serializersModule = KotoolsTypesSerializers.all }
     * val encoded: String = format.encodeToString(Zero)
     * println(encoded) // 0
     * val decoded: Zero = format.decodeFromString(encoded)
     * println(Zero === decoded) // true
     * ```
     */
    public val all: SerializersModule
        get() = SerializersModule { include(zero) }

    /**
     * Returns the module for serializing the [Zero] type.
     *
     * Here's an example of calling this property from Kotlin code:
     *
     * ```kotlin
     * val format = Json { serializersModule = KotoolsTypesSerializers.zero }
     * val encoded: String = format.encodeToString(Zero)
     * println(encoded) // 0
     * val decoded: Zero = format.decodeFromString(encoded)
     * println(Zero === decoded) // true
     * ```
     */
    public val zero: SerializersModule
        get() = SerializersModule { contextual(ZeroAsByteSerializer) }

    /**
     * Returns the string representation of this object.
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val message: String = KotoolsTypesSerializers.toString()
     * println(message) // KotoolsTypesSerializers
     * ```
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String =
        simpleNameOf<KotoolsTypesSerializers>()
}
