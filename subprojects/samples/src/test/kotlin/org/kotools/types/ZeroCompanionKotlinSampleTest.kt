package org.kotools.types

import kotlin.test.Test

class ZeroCompanionKotlinSampleTest {
    @Test
    fun `PATTERN should pass`() {
        val expected = "^[+-]?0+(?:\\.0+)?\$"
        assertPrints(expected, ZeroCompanionKotlinSample::pattern)
    }

    @Test
    fun `fromByte(Byte) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromByte)

    @Test
    fun `fromByteOrNull(Byte) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromByteOrNull)

    @Test
    fun `fromShort(Short) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromShort)

    @Test
    fun `fromShortOrNull(Short) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromShortOrNull)

    @Test
    fun `fromInt(Int) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromInt)

    @Test
    fun `fromIntOrNull(Int) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromIntOrNull)

    @Test
    fun `fromLong(Long) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromLong)

    @Test
    fun `fromLongOrNull(Long) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromLongOrNull)

    @Test
    fun `fromFloat(Float) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromFloat)

    @Test
    fun `fromFloatOrNull(Float) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromFloatOrNull)

    @Test
    fun `fromDouble(Double) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromDouble)

    @Test
    fun `fromDoubleOrNull(Double) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromDoubleOrNull)

    @Test
    fun `fromStringOrNull(Any) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromStringOrNull)
}
