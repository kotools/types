package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals

class NonZeroIntegerJsSample {
    @Suppress("DIVISION_BY_ZERO")
    @Test
    fun divisionProblem() {
        // JS platform
        assertEquals(expected = 0, 12 / 0)
        assertEquals(expected = 0, 12 % 0)
    }
}
