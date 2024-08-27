package org.kotools.types.kotlinx.serialization

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
internal class EmailAddressAsStringSerializerCommonSample {
    @Test
    fun primaryConstructor() {
        EmailAddressAsStringSerializer()
    }
}
