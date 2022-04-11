package io.github.kotools.assert

/** Asserts that the current value is equal to the [other] value. */
public infix fun <T : Any> T.assertEquals(other: T): Unit =
    kotlin.test.assertEquals(other, this)

/**
 * Asserts that the current value is equal to the [other] value, or report the
 * [message] if not.
 */
public fun <T : Any> T.assertEquals(other: T, message: String): Unit =
    kotlin.test.assertEquals(other, this, message)
