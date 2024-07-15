package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(InternalKotoolsTypesApi::class)
class InvalidZeroRepresentationTest {
    @Test
    fun constructorShouldPass() {
        val number: Any = setOf(Int.MIN_VALUE..-1, 1..Int.MAX_VALUE)
            .random()
            .random()
        InvalidZeroRepresentation(number)
    }

    @Test
    fun toStringShouldPass() {
        val number: Any = setOf(Int.MIN_VALUE..-1, 1..Int.MAX_VALUE)
            .random()
            .random()
        val actual: String = InvalidZeroRepresentation(number)
            .toString()
        val expected = "'$number' is not a valid representation of zero."
        assertEquals(expected, actual)
    }
}
