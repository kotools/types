package io.github.kotools.assert

import kotlin.test.assertNotEquals

/** Asserts that the current value isn't equal to the [other] value. */
public infix fun <T : Any> T.assertNotEquals(other: T): Unit =
    assertNotEquals(other, this)

/**
 * Asserts that the current value isn't equal to the [other] value, or report
 * the [message] if not.
 */
public fun <T : Any> T.assertNotEquals(other: T, message: String): Unit =
    assertNotEquals(other, this, message)

/**
 * Asserts that the current value isn't equal to the [other] value, or compute
 * and report the [lazyMessage]'s result if not.
 */
public fun <T : Any> T.assertNotEquals(
    other: T,
    lazyMessage: () -> String
): Unit = assertNotEquals(other, this, lazyMessage())
