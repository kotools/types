@file:JvmName("EmailAddressRegexSerializers")

package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.kotlinx.serialization.internal.EmailAddressRegexAsStringSerializer
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

/**
 * Returns an object responsible for serializing the [EmailAddressRegex] type as
 * [String].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of Kotlin code that encodes and decodes the
 * [EmailAddressRegex] type using the
 * [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json)
 * and this function:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.EmailAddressRegexSerializersSample.stringSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun EmailAddressRegex.Companion.stringSerializer():
        KSerializer<EmailAddressRegex> = EmailAddressRegexAsStringSerializer()
