package org.kotools.types.number

import kotlin.test.Test

class IntegerJsSample {
    // -------------------------- Type documentation ---------------------------

    @Suppress("DIVISION_BY_ZERO")
    @Test
    fun divisionByZeroProblem() {
        // JS platform
        check(12 / 0 == 0)
        check(12 % 0 == 0)
    }
}
