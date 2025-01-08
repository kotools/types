package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotools.types.internal.simpleNameOf
import org.kotools.types.ExperimentalKotoolsTypesApi
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
     * SAMPLE: [org.kotools.types.kotlinx.serialization.KotoolsTypesSerializersCommonSample.all]
     * </details>
     */
    public val all: SerializersModule
        get() = SerializersModule {
            val emailAddressSerializer = EmailAddressAsStringSerializer()
            this.contextual(emailAddressSerializer)
            val zeroSerializer = ZeroAsByteSerializer()
            this.contextual(zeroSerializer)
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
     * Here's an example of calling this method from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.kotlinx.serialization.KotoolsTypesSerializersCommonSample.toStringOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = simpleNameOf(this::class)
}
