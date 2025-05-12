package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class NegativeIntegerCommonSample {
    // ------------------------------- Companion -------------------------------

    @Test
    fun orNullInt() {
        val number: Int = (Int.MIN_VALUE..-1).random()
        val integer: NegativeInteger? = NegativeInteger.orNull(number)
        assertNotNull(integer)
    }
}
