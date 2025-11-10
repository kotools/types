@file:JvmName("SerializersModule")

package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.kotools.types.EmailAddress
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import org.kotools.types.kotlinx.serialization.internal.EmailAddressAsStringSerializer
import kotlin.jvm.JvmName

/**
 * Returns a collection of default serializers for types located in
 * `org.kotools.types` package.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>EmailAddress</b>
 * </summary>
 *
 * This function provides an object for serializing and deserializing an
 * [EmailAddress] as [String].
 *
 * Here's an example of calling it, using the JavaScript Object Notation (JSON)
 * format:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.SerializersModuleSample.emailAddressAsString]
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>EmailAddressRegex</b>
 * </summary>
 *
 * This function provides an object for serializing and deserializing an
 * [EmailAddressRegex] as [String].
 *
 * Here's an example of calling it, using the JavaScript Object Notation (JSON)
 * format:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.SerializersModuleSample.emailAddressRegexAsString]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@Suppress(Warning.FUNCTION_NAME)
public fun KotoolsTypesSerializersModule(): SerializersModule =
    SerializersModule {
        this.contextual(EmailAddressAsStringSerializer())
        EmailAddressRegex.stringSerializer()
            .let(this::contextual)
    }
