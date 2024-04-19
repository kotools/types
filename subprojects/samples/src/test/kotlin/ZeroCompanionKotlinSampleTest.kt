package org.kotools.types

import kotlin.test.Test

class ZeroCompanionKotlinSampleTest {
    @Test
    fun `fromByteOrNull(Byte) should pass`(): Unit =
        assertPrintsTrue(ZeroCompanionKotlinSample::fromByteOrNull)
}
