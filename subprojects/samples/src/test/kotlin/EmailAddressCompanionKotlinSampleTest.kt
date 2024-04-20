package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.simpleNameOf
import kotlin.test.Test

class EmailAddressCompanionKotlinSampleTest {
    @Test
    fun patternSample_should_pass() {
        val expected = "^\\S+@\\S+\\.\\S+\$"
        assertPrints(expected, EmailAddressCompanionKotlinSample::patternSample)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun `qualifiedName should pass`() {
        val expected: String = checkNotNull(EmailAddress::class.qualifiedName) {
            val type: String = simpleNameOf<EmailAddress>()
            "Getting qualified name of '$type' shouldn't return null."
        }
        assertPrints(expected, EmailAddressCompanionKotlinSample::qualifiedName)
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
}
