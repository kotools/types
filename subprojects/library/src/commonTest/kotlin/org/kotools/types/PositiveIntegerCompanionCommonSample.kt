package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerCompanionCommonSample {
    @Test
    fun orNullInt() {
        val number: Int = (1..Int.MAX_VALUE).random()
        val integer: PositiveInteger? = PositiveInteger.orNull(number)
        val message = "$number is greater than zero."
        assertNotNull(integer, message)
    }

    @Test
    fun orNullLong() {
        val number: Long = (1..Long.MAX_VALUE).random()
        val integer: PositiveInteger? = PositiveInteger.orNull(number)
        val message = "$number is greater than zero."
        assertNotNull(integer, message)
    }

    @Test
    fun orNullString() {
        val text: String = (1..Long.MAX_VALUE).random()
            .let { "+${it}1234567890" }
        val integer: PositiveInteger? = PositiveInteger.orNull(text)
        val message = "'$text' is an integer greater than zero."
        assertNotNull(integer, message)
    }

    @Test
    fun orThrowInt() {
        val number: Int = (1..Int.MAX_VALUE).random()
        PositiveInteger.orThrow(number)
    }

    @Test
    fun orThrowLong() {
        val number: Long = (1..Long.MAX_VALUE).random()
        PositiveInteger.orThrow(number)
    }
}
