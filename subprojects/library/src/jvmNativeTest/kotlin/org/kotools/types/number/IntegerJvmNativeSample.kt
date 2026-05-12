package org.kotools.types.number

import kotlin.test.Test

class IntegerJvmNativeSample {
    @Suppress("DIVISION_BY_ZERO")
    @Test
    fun divisionByZeroProblem() {
        // JVM and Native platforms
        val quotient: Result<Int> = runCatching { 12 / 0 }
        val remainder: Result<Int> = runCatching { 12 % 0 }
        check(quotient.exceptionOrNull() is ArithmeticException)
        check(remainder.exceptionOrNull() is ArithmeticException)
    }
}
