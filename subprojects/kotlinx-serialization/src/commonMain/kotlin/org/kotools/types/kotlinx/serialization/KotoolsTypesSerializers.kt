package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.simpleNameOf
import org.kotools.types.EmailAddress
import org.kotools.types.Zero
import org.kotools.types.internal.DeprecatedAsErrorSince
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

    /** Returns the module for serializing the [EmailAddress] type. */
    @Deprecated(
        "Use the 'EmailAddressAsStringSerializer' type instead.",
        ReplaceWith(
            "SerializersModule { contextual(EmailAddressAsStringSerializer()) }",
            "kotlinx.serialization.modules.SerializersModule",
            "kotlinx.serialization.modules.contextual",
            "org.kotools.types.kotlinx.serialization.EmailAddressAsStringSerializer"
        ),
        DeprecationLevel.ERROR
    )
    @DeprecatedAsErrorSince(KotoolsTypesVersion.V4_5_3)
    public val emailAddress: SerializersModule
        get() = SerializersModule {
            contextual(EmailAddressAsStringSerializer)
        }

    /** Returns the module for serializing the [Zero] type. */
    @Deprecated(
        "Use the 'ZeroAsByteSerializer' type instead.",
        ReplaceWith(
            "SerializersModule { contextual(ZeroAsByteSerializer()) }",
            "kotlinx.serialization.modules.SerializersModule",
            "kotlinx.serialization.modules.contextual",
            "org.kotools.types.kotlinx.serialization.ZeroAsByteSerializer"
        ),
        DeprecationLevel.ERROR
    )
    @DeprecatedAsErrorSince(KotoolsTypesVersion.V4_5_3)
    public val zero: SerializersModule
        get() = SerializersModule { contextual(ZeroAsByteSerializer) }

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
    final override fun toString(): String =
        simpleNameOf<KotoolsTypesSerializers>()
}
