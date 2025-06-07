@file:JvmName("PositiveIntegerSerializers")

package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.PositiveInteger
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.kotlinx.serialization.internal.PositiveIntegerAsStringSerializer
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

/**
 * Returns an object responsible for serializing the [PositiveInteger] type as
 * [String].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of Kotlin code that encodes and decodes the
 * [PositiveInteger] type using the
 * [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json)
 * and this function:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.PositiveIntegerSerializersSample.stringSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_2)
@JvmSynthetic
public fun PositiveInteger.Companion.stringSerializer():
        KSerializer<PositiveInteger> = PositiveIntegerAsStringSerializer()
