package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.descriptors.PrimitiveKind
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

class EmailAddressRegexSerializersTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun stringSerializerDescriptor(): Unit = EmailAddressRegex
        .stringSerializer()
        .assertDescriptor(PrimitiveKind.STRING)
}
