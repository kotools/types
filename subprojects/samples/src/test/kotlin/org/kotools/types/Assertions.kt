package org.kotools.types

import com.github.stefanbirkner.systemlambda.SystemLambda
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal fun assertPrints(expected: Any, block: () -> Unit) {
    val actual: String = SystemLambda.tapSystemOut(block)
        .trim()
    assertEquals("$expected", actual)
}

internal fun assertPrintsTrue(block: () -> Unit) {
    val actual: Boolean = SystemLambda.tapSystemOut(block)
        .trim()
        .toBooleanStrict()
    assertTrue(actual, message = "Kotlin code sample should print 'true'.")
}
