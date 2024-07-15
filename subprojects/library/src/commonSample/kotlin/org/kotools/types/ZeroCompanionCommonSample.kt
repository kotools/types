package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroCompanionCommonSample {
    @Test
    fun pattern() {
        val numbers: List<Any> = listOf(
            0, 0.0,
            "+0", "+000", "+0.000", "+000.000", // with unary plus
            "-0", "-000", "-0.000", "-000.000" // with unary minus
        )
        val regex = Regex(Zero.PATTERN)
        val numbersAreValid: Boolean = numbers.all { "$it" matches regex }
        assertTrue(numbersAreValid)
    }
}
