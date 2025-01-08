package org.kotools.types.kotlinx.serialization

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroAsStringSerializerCommonSample {
    @Test
    fun primaryConstructor() {
        ZeroAsStringSerializer()
    }
}
