@file:JvmName("PositiveIntegerSerializers")

package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.PositiveInteger
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.kotlinx.serialization.internal.StringSerializer
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

/**
 * Returns an object responsible for serializing the [PositiveInteger] type as
 * [String].
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.PositiveIntegerSerializersSample.stringSerializer]
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_1_0)
@JvmSynthetic
public fun PositiveInteger.Companion.stringSerializer():
        KSerializer<PositiveInteger> = PositiveIntegerAsStringSerializer

@ExperimentalKotoolsTypesApi
private object PositiveIntegerAsStringSerializer :
    StringSerializer<PositiveInteger> {
    override fun deserialize(text: String): PositiveInteger =
        requireNotNull(PositiveInteger of text) {
            "'$text' doesn't represent an integer greater than zero."
        }
}
