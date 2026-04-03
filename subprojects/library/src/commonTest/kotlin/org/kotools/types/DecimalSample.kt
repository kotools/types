package org.kotools.types

import kotlin.test.Test

class DecimalSample {
    // ----------------------------- Documentation -----------------------------

    @Suppress("SimplifyBooleanWithConstants")
    @Test
    fun representationProblem() {
        check(1.1 + 2.2 == 3.3000000000000003) // instead of being exactly 3.3
    }
}
