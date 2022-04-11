package io.github.kotools.assert

/** Asserts that the current value isn't equal to the [other] value. */
public infix fun <T : Any> T.assertNotEquals(other: T): Unit =
    kotlin.test.assertNotEquals(other, this)

/**
 * Asserts that the current value isn't equal to the [other] value, or report
 * the [message] if not.
 */
public fun <T : Any> T.assertNotEquals(other: T, message: String): Unit =
    kotlin.test.assertNotEquals(other, this, message)
