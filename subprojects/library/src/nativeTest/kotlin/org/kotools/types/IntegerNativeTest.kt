package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerNativeTest {
    @Test
    fun constructorLongFails() {
        val number: Long = Long.MAX_VALUE
        val error: NotImplementedError = assertFailsWith { Integer(number) }
        val actual: String? = error.message
        val expected = "Not yet supported on Kotlin/Native."
        assertEquals(expected, actual)
    }
}
