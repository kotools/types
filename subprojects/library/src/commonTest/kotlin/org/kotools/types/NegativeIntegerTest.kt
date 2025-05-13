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

    // ------------------------ Companion.orNull(Long) -------------------------

    @Test
    fun orNullShouldPassWithLongThatIsLessThanZero() {
        val number: Long = (Long.MIN_VALUE..-1).random()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNotNull(actual)
    }

    @Test
    fun orNullShouldFailWithLongThatEqualsZero() {
        val number: Long = Zero()
            .toLong()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullShouldFailWithLongThatIsGreaterThanZero() {
        val number: Long = (1..Long.MAX_VALUE).random()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNull(actual)
    }
}
