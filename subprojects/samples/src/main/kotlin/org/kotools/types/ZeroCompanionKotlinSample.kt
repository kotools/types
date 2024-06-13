package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object ZeroCompanionKotlinSample {
    fun pattern() {
        val numbers: List<Any> = listOf(
            0, 0.0,
            "+0", "+000", "+0.000", "+000.000", // with unary plus
            "-0", "-000", "-0.000", "-000.000" // with unary minus
        )
        val regex = Regex(Zero.PATTERN)
        val numbersAreValid: Boolean = numbers.all { "$it" matches regex }
        println(numbersAreValid) // true
    } // END

    fun orNull() {
        val number: Any = "-000.000"
        val zero: Zero? = Zero.orNull(number)
        println(zero != null) // true
    } // END

    fun orThrow() {
        val number: Any = "-000.000"
        val isSuccess: Boolean = try {
            Zero.orThrow(number)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        println(isSuccess) // true
    } // END
}
