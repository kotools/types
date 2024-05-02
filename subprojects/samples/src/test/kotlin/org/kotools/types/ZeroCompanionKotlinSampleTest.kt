package org.kotools.types

import kotlin.test.Test

class ZeroCompanionKotlinSampleTest {
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
    fun `fromLongOrNull(Long) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromLongOrNull)
}
