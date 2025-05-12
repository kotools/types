package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class NegativeIntegerTest {
    // ------------------------- Companion.orNull(Int) -------------------------

    @Test
    fun orNullShouldPassWithIntThatIsLessThanZero() {
        val number: Int = (Int.MIN_VALUE..-1).random()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNotNull(actual)
    }

    @Test
    fun orNullShouldFailWithIntThatEqualsZero() {
        val number: Int = Zero()
            .toInt()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullShouldFailWithIntThatIsGreaterThanZero() {
        val number: Int = (1..Int.MAX_VALUE).random()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNull(actual)
    }
}
