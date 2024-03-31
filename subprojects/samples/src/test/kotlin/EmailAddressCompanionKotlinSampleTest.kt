package org.kotools.types

import com.github.stefanbirkner.systemlambda.SystemLambda
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EmailAddressCompanionKotlinSampleTest {
    @Test
    fun patternSample_should_pass() {
        val actual: String = SystemLambda
            .tapSystemOut(EmailAddressCompanionKotlinSample::patternSample)
            .trim()
        val expected = "^\\S+@\\S+\\.\\S+\$"
        assertEquals(expected, actual)
    }

    @Test
    fun fromStringOrNullSample_should_pass() {
        val actual: Boolean = SystemLambda
            .tapSystemOut(
                EmailAddressCompanionKotlinSample::fromStringOrNullSample
            )
            .trim()
            .toBooleanStrict()
        assertTrue(actual)
    }
}
