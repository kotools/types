package org.kotools.types

import kotlin.test.Test

class ZeroKotlinSampleTest {
    @Test
    fun `equals(nullable Any) should pass`(): Unit =
        assertPrintsTrue(ZeroKotlinSample::equalsOverride)

    @Test
    fun `hashCode() should pass`(): Unit =
        assertPrintsTrue(ZeroKotlinSample::hashCodeOverride)

    @Test
    fun `toByte() should pass`() {
        val expected = "0"
        assertPrints(expected, ZeroKotlinSample::toByte)
    }

    @Test
    fun `toShort() should pass`(): Unit =
        assertPrintsTrue(ZeroKotlinSample::toShort)

    @Test
    fun toStringSample_should_pass() {
        val expected = "0"
        assertPrints(expected, ZeroKotlinSample::toStringSample)
    }
}
