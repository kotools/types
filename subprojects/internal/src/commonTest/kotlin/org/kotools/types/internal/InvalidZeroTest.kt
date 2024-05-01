package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(InternalKotoolsTypesApi::class)
class InvalidZeroTest {
    @Test
    fun toString_should_pass() {
        val number: Int = Random.nextInt(1..Int.MAX_VALUE)
        val actual: String = InvalidZero(number)
            .toString()
        val expected = "'$number' shouldn't be other than zero."
        assertEquals(expected, actual)
    }
}
