package io.github.kotools.assert

/** Asserts that the current value isn't equal to the [other] value. */
public infix fun <T : Any> T.assertNotEquals(other: T): Unit =
    assertNotEquals(other, "$this shouldn't be equal to $other")

/**
 * Asserts that the current value isn't equal to the [other] value, or compute
 * and report the [lazyMessage]'s result if not.
 */
@Deprecated(
    "Give the message directly as a parameter instead of a lazy one.",
    ReplaceWith("assertNotEquals(other, message)")
)
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
