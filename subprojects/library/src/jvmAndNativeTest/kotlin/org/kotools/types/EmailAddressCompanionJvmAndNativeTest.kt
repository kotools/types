package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.simpleNameOf
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressCompanionJvmAndNativeTest {
    @Test
    fun `qualifiedName should return the qualified name of EmailAddress`() {
        val actual: String = EmailAddress.qualifiedName
        val expected: String = checkNotNull(EmailAddress::class.qualifiedName) {
            val emailAddressSimpleName: String = simpleNameOf<EmailAddress>()
            "Getting qualified name of '$emailAddressSimpleName' shouldn't " +
                    "return null."
        }
        assertEquals(expected, actual)
    }
}
