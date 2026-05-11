package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerSample {
    // ------------------------------- Creations -------------------------------

    @Test
    fun of() {
        val value = 9_223_372_036_854_775_807

        val result: Integer = Integer.of(value)

        val resultString: String = result.toString()
        val expected: String = value.toString()
        check(resultString == expected)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        val value = 9_223_372_036_854_775_807
        val integer: Integer = Integer.of(value)

        val result: String = integer.toString()

        val expected: String = value.toString()
        check(result == expected)
    }
}
