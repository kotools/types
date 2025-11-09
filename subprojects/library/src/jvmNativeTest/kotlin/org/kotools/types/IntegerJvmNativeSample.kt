package org.kotools.types

import kotlin.test.Test

class IntegerJvmNativeSample {
    @Suppress("DIVISION_BY_ZERO")
    @Test
    fun divisionByZeroProblem() {
        // JVM and Native platforms
        val quotient: Result<Int> = kotlin.runCatching { 12 / 0 }
        val remainder: Result<Int> = kotlin.runCatching { 12 % 0 }
        check(quotient.exceptionOrNull() is ArithmeticException)
        check(remainder.exceptionOrNull() is ArithmeticException)
    }
}
