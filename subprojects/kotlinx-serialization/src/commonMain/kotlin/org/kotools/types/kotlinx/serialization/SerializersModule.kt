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
import kotlin.jvm.JvmName

/**
 * Returns a collection of default serializers used for serializing types
 * provided by Kotools Types.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Default serializers</b>
 * </summary>
 *
 * Here's the list of serializers used by default for each type:
 * - [EmailAddress] - [EmailAddress.Companion.stringSerializer]
 * - [EmailAddressRegex] - [EmailAddressRegex.Companion.stringSerializer]
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of Kotlin code that encodes and decodes the [EmailAddress]
 * type using the [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json)
 * and this function:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.SerializersModuleSample.kotoolsTypesSerializersModule]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@Suppress(Warning.FUNCTION_NAME)
public fun KotoolsTypesSerializersModule(): SerializersModule =
    SerializersModule {
        EmailAddress.stringSerializer()
            .let(this::contextual)
        EmailAddressRegex.stringSerializer()
            .let(this::contextual)
    }
