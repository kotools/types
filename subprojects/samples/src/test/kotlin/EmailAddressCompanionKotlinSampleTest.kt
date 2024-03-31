package org.kotools.types

import com.github.stefanbirkner.systemlambda.SystemLambda
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private typealias Sample = EmailAddressCompanionKotlinSample

class EmailAddressCompanionKotlinSampleTest {
    @Test
    fun patternSample_should_pass() {
        val actual: String = SystemLambda.tapSystemOut(Sample::patternSample)
            .trim()
        val expected = "^\\S+@\\S+\\.\\S+\$"
        assertEquals(expected, actual)
    }

    @Test
    fun fromStringSample_should_pass() {
        val actual: Boolean = SystemLambda
            .tapSystemOut(Sample::fromStringSample)
            .trim()
            .toBooleanStrict()
        assertTrue(actual)
    }

    @Test
    fun fromStringOrNullSample_should_pass() {
        val actual: Boolean = SystemLambda
            .tapSystemOut(Sample::fromStringOrNullSample)
            .trim()
            .toBooleanStrict()
        assertTrue(actual)
    }

    @Test
    fun `fromStringOrNull(Any, Any) sample should pass`() {
        val actual: Boolean = SystemLambda
            .tapSystemOut(Sample::fromStringOrNull_Any_Any_Sample)
            .trim()
            .toBooleanStrict()
        assertTrue(actual)
    }
}
