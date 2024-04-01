package org.kotools.types

import kotlin.test.Test

class EmailAddressKotlinSampleTest {
    @Test
    fun `toString() should pass`(): Unit =
        assertPrintsTrue(EmailAddressKotlinSample::toString_override)
}
