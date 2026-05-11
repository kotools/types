package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
    // -------------------------- Creation from Long ---------------------------

    @Test
    fun creationFromLongPreservesCanonicalRepresentation(): Unit =
        repeat(times = 1_000) {
            val value: Long = Random.nextLong()

            val result: Integer = Integer.of(value)

            val actual: String = result.toString()
            val expected: String = value.toString()
            assertEquals(expected, actual)
        }
}
