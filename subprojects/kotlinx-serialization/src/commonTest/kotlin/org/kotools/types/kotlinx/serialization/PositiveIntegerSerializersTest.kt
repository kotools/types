package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.descriptors.PrimitiveKind
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.PositiveInteger
import kotlin.test.Test

class PositiveIntegerSerializersTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun stringSerializerDescriptor(): Unit = PositiveInteger.stringSerializer()
        .assertDescriptor(PrimitiveKind.STRING)
}
