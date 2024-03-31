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
    fun `fromString(Any) should pass`() {
        val actual: Boolean = SystemLambda.tapSystemOut(Sample::fromString_Any)
            .trim()
            .toBooleanStrict()
        assertTrue(actual)
    }

    @Test
    fun `fromStringOrNull(Any) should pass`() {
        val actual: Boolean = SystemLambda
            .tapSystemOut(Sample::fromStringOrNull_Any)
            .trim()
            .toBooleanStrict()
        assertTrue(actual)
    }

    @Test
    fun `fromStringOrNull(Any, Any) should pass`() {
        val actual: Boolean = SystemLambda
            .tapSystemOut(Sample::fromStringOrNull_Any_Any)
            .trim()
            .toBooleanStrict()
        assertTrue(actual)
    }
}
