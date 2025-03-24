package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotools.types.internal.simpleNameOf
import org.kotools.types.EmailAddress
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning

/**
 * Contains modules for serializing the types provided by Kotools Types using
 * the kotlinx.serialization library.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_5_1)
public object KotoolsTypesSerializers {
    /**
     * Returns the module for serializing all types.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this property from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.kotlinx.serialization.KotoolsTypesSerializersSample.all]
     * </details>
     */
    public val all: SerializersModule
        get() = SerializersModule {
            EmailAddress.stringSerializer()
                .let(this::contextual)
            Zero.byteSerializer()
                .let(this::contextual)
        }

    /**
     * Returns the string representation of this object.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.kotlinx.serialization.KotoolsTypesSerializersSample.toStringOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = simpleNameOf(this::class)
}
