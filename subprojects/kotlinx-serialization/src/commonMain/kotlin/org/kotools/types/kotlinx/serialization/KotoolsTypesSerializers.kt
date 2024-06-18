package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.simpleNameOf
import org.kotools.types.EmailAddress
import org.kotools.types.Zero
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import org.kotools.types.kotlinx.serialization.internal.EmailAddressAsStringSerializer
import org.kotools.types.kotlinx.serialization.internal.ZeroAsByteSerializer

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
     * Here's an example of calling this property from Kotlin code:
     *
     * SAMPLE: KotoolsTypesSerializersKotlinSample.all.md
     */
    public val all: SerializersModule
        get() = SerializersModule {
            include(this@KotoolsTypesSerializers.emailAddress)
            include(this@KotoolsTypesSerializers.zero)
        }

    /**
     * Returns the module for serializing the [EmailAddress] type.
     *
     * Here's an example of calling this property from Kotlin code:
     *
     * SAMPLE: KotoolsTypesSerializersKotlinSample.emailAddress.md
     */
    public val emailAddress: SerializersModule
        get() = SerializersModule {
            contextual(EmailAddressAsStringSerializer)
        }

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
    @Suppress(Warning.FINAL)
    final override fun toString(): String =
        simpleNameOf<KotoolsTypesSerializers>()
}
