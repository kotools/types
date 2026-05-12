package org.kotools.types

import kotlin.test.Test

class IntegerJsSample {
    @Suppress("DIVISION_BY_ZERO")
    @Test
    fun divisionByZeroProblem() {
        // JS platform
        check(12 / 0 == 0)
        check(12 % 0 == 0)
    }
}
