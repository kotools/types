package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.descriptors.PrimitiveKind
import org.kotools.types.EmailAddress
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

class EmailAddressSerializersTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun stringSerializerDescriptor(): Unit = EmailAddress.stringSerializer()
        .assertDescriptor(PrimitiveKind.STRING)
}
