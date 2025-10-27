package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertFailsWith

class NonZeroIntegerJvmNativeSample {
    @Suppress("DIVISION_BY_ZERO")
    @Test
    fun divisionProblem() {
        // JVM and Native platforms
        assertFailsWith<ArithmeticException> { 12 / 0 }
        assertFailsWith<ArithmeticException> { 12 % 0 }
    }
}
