package org.kotools.types

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class TemporarySerializationTest {
    @Test
    fun serialization_of_Int_should_pass() {
        val number: Int = Random.nextInt()
        val actual: String = Json.encodeToString(number)
        val expected: String = number.toString()
        assertEquals(expected, actual)
    }
}
