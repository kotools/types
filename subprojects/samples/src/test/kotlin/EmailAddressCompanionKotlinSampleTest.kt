package org.kotools.types

import kotlin.test.Test

class EmailAddressCompanionKotlinSampleTest {
    @Test
    fun patternSample_should_pass() {
        val expected = "^\\S+@\\S+\\.\\S+\$"
        assertPrints(expected, EmailAddressCompanionKotlinSample::patternSample)
    }

    @Test
    fun `fromString(Any) should pass`(): Unit =
        assertPrintsTrue(EmailAddressCompanionKotlinSample::fromString_Any)

    @Test
    fun `fromString(Any, Any) should pass`(): Unit =
        assertPrintsTrue(EmailAddressCompanionKotlinSample::fromString_Any_Any)

    @Test
    fun `fromStringOrNull(Any) should pass`(): Unit = assertPrintsTrue(
        EmailAddressCompanionKotlinSample::fromStringOrNull_Any
    )

    @Test
    fun `fromStringOrNull(Any, Any) should pass`(): Unit = assertPrintsTrue(
        EmailAddressCompanionKotlinSample::fromStringOrNull_Any_Any
    )

    @Test
    fun `orNull(String) should pass`(): Unit =
        assertPrintsTrue(EmailAddressCompanionKotlinSample::orNull_String)
}
