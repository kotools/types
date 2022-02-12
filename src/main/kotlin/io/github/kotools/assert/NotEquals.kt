package io.github.kotools.assert

/** Asserts that the current value isn't equal to the [other] value. */
public infix fun <T : Any> T.assertNotEquals(other: T): Unit =
    assertNotEquals(other) { "$this shouldn't be equal to $it" }

/**
 * Asserts that the current value isn't equal to the [other] value, or compute
 * and report the [lazyMessage]'s result if not.
 */
public inline fun <T : Any> T.assertNotEquals(
    other: T,
    lazyMessage: T.(T) -> String
): Unit = kotlin.test.assertNotEquals(other, this, lazyMessage(other))

/**
 * Asserts that the current value isn't equal to the [other] value, or report
 * the [message] if not.
 */
public fun <T : Any> T.assertNotEquals(other: T, message: String): Unit =
    kotlin.test.assertNotEquals(other, this, message)
