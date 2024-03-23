package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertTrue

class TemporaryTest {
    @Test
    fun shouldPass(): Unit =
        assertTrue(actual = Temporary.VALUE, message = "Test should pass.")
}
