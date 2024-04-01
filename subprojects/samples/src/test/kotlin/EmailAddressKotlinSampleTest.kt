package org.kotools.types

import com.github.stefanbirkner.systemlambda.SystemLambda
import kotlin.test.Test
import kotlin.test.assertTrue

class EmailAddressKotlinSampleTest {
    @Test
    fun `toString() should pass`() {
        val actual: Boolean = SystemLambda
            .tapSystemOut(EmailAddressKotlinSample::toString_override)
            .trim()
            .toBooleanStrict()
        assertTrue(actual)
    }
}
