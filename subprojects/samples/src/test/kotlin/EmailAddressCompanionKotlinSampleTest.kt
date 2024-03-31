package org.kotools.types

import com.github.stefanbirkner.systemlambda.SystemLambda
import kotlin.test.Test
import kotlin.test.assertEquals

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
        val actual: String = SystemLambda.tapSystemOut(
            EmailAddressCompanionKotlinSample::fromStringOrNullSample
        ).trim()
        val expected = "true"
        assertEquals(expected, actual)
    }
}
