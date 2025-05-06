package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerCommonSample {
    @Test
    fun toStringOverride() {
        val number: Int = (1..Int.MAX_VALUE).random()
        val integerAsString: String = PositiveInteger.orThrow(number)
            .toString()
        assertEquals(expected = "$number", integerAsString)
    }
}
