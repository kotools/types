package org.kotools.types

import com.github.stefanbirkner.systemlambda.SystemLambda
import kotlin.test.Test
import kotlin.test.assertContentEquals

class EmailAddressKotlinSampleTest {
    @Test
    fun `(de)serialization processes should pass`() {
        val actual: List<String> = SystemLambda
            .tapSystemOut(EmailAddressKotlinSample::serialization)
            .trim()
            .lines()
        val expected: List<String> = listOf("\"contact@kotools.org\"", "true")
        assertContentEquals(expected, actual)
    }

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
