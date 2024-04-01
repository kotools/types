package org.kotools.types

import kotlin.test.Test

class EmailAddressKotlinSampleTest {
    @Test
    fun `equals(nullable Any) should pass`(): Unit =
        assertPrintsTrue(EmailAddressKotlinSample::equals_override)

    @Test
    fun `hashCode() should pass`(): Unit =
        assertPrintsTrue(EmailAddressKotlinSample::hashCode_override)

    @Test
    fun `toString() should pass`(): Unit =
        assertPrintsTrue(EmailAddressKotlinSample::toString_override)
}
