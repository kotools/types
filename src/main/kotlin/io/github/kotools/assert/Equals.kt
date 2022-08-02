package io.github.kotools.assert

import kotools.assert.SinceKotoolsAssert

/** Asserts that the current value is equal to the [other] value. */
@SinceKotoolsAssert("1.0")
public infix fun <T : Any> T.assertEquals(other: T): Unit =
    kotlin.test.assertEquals(other, this)

/**
 * Asserts that the current value is equal to the [other] value, or report the
 * [message] if not.
 */
@SinceKotoolsAssert("1.0")
public fun <T : Any> T.assertEquals(other: T, message: String): Unit =
    kotlin.test.assertEquals(other, this, message)
