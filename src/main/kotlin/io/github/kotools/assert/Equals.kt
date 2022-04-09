package io.github.kotools.assert

/** Asserts that the current value is equal to the [other] value. */
public infix fun <T : Any> T.assertEquals(other: T): Unit =
    kotlin.test.assertEquals(other, this)

/**
 * Asserts that the current value is equal to the [other] value, or compute and
 * report the [lazyMessage]'s result if not.
 */
@Deprecated(
    "Give the message directly as a parameter instead of a lazy one.",
    ReplaceWith("assertEquals(other, message)")
)
public inline fun <T : Any> T.assertEquals(
    other: T,
    lazyMessage: T.(T) -> String
): Unit = kotlin.test.assertEquals(other, this, lazyMessage(other))

/**
 * Asserts that the current value is equal to the [other] value, or report the
 * [message] if not.
 */
public fun <T : Any> T.assertEquals(other: T, message: String): Unit =
    kotlin.test.assertEquals(other, this, message)
