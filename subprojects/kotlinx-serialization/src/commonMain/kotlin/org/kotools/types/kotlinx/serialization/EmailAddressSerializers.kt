@file:JvmName("EmailAddressSerializers")

package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import org.kotools.types.EmailAddress
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.kotlinx.serialization.internal.EmailAddressAsStringSerializer
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

/**
 * Returns an object responsible for serializing the [EmailAddress] type as
 * [String].
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
 * SAMPLE: [org.kotools.types.kotlinx.serialization.EmailAddressSerializersSample.stringSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun EmailAddress.Companion.stringSerializer():
        KSerializer<EmailAddress> = EmailAddressAsStringSerializer()
