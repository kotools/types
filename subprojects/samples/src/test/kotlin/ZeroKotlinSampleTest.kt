package org.kotools.types

import kotlin.test.Test

class ZeroKotlinSampleTest {
    @Test
    fun `toByte() should pass`() {
        val expected = "0"
        assertPrints(expected, ZeroKotlinSample::toByte)
    }

    @Test
    fun toStringSample_should_pass() {
        val expected = "0"
        assertPrints(expected, ZeroKotlinSample::toStringSample)
    }
}
