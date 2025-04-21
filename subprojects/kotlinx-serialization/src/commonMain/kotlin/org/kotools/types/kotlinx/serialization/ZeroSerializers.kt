@file:JvmName("ZeroSerializers")

package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.kotlinx.serialization.internal.ZeroAsByteSerializer
import org.kotools.types.kotlinx.serialization.internal.ZeroAsDoubleSerializer
import org.kotools.types.kotlinx.serialization.internal.ZeroAsFloatSerializer
import org.kotools.types.kotlinx.serialization.internal.ZeroAsIntSerializer
import org.kotools.types.kotlinx.serialization.internal.ZeroAsLongSerializer
import org.kotools.types.kotlinx.serialization.internal.ZeroAsShortSerializer
import org.kotools.types.kotlinx.serialization.internal.ZeroAsStringSerializer
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

/**
 * Returns an object responsible for serializing the [Zero] type as [Byte].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of Kotlin code that encodes and decodes the [Zero] type
 * using the [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json)
 * and this function:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroSerializersSample.byteSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun Zero.Companion.byteSerializer(): KSerializer<Zero> =
    ZeroAsByteSerializer()

/**
 * Returns an object responsible for serializing the [Zero] type as [Short].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of Kotlin code that encodes and decodes the [Zero] type
 * using the [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json)
 * and this function:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroSerializersSample.shortSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun Zero.Companion.shortSerializer(): KSerializer<Zero> =
    ZeroAsShortSerializer()

/**
 * Returns an object responsible for serializing the [Zero] type as [Int].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of Kotlin code that encodes and decodes the [Zero] type
 * using the [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json)
 * and this function:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroSerializersSample.intSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun Zero.Companion.intSerializer(): KSerializer<Zero> =
    ZeroAsIntSerializer()

/**
 * Returns an object responsible for serializing the [Zero] type as [Long].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of Kotlin code that encodes and decodes the [Zero] type
 * using the [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json)
 * and this function:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroSerializersSample.longSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun Zero.Companion.longSerializer(): KSerializer<Zero> =
    ZeroAsLongSerializer()

/**
 * Returns an object responsible for serializing the [Zero] type as [Float].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of Kotlin code that encodes and decodes the [Zero] type
 * using the [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json)
 * and this function:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroSerializersSample.floatSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun Zero.Companion.floatSerializer(): KSerializer<Zero> =
    ZeroAsFloatSerializer()

/**
 * Returns an object responsible for serializing the [Zero] type as [Double].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of Kotlin code that encodes and decodes the [Zero] type
 * using the [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json)
 * and this function:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroSerializersSample.doubleSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun Zero.Companion.doubleSerializer(): KSerializer<Zero> =
    ZeroAsDoubleSerializer()

/**
 * Returns an object responsible for serializing the [Zero] type as [String].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of Kotlin code that encodes and decodes the [Zero] type
 * using the [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json)
 * and this function:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroSerializersSample.stringSerializer]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_1)
@JvmSynthetic
public fun Zero.Companion.stringSerializer(): KSerializer<Zero> =
    ZeroAsStringSerializer()
