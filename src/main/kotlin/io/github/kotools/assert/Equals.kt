package io.github.kotools.assert

import kotools.assert.SinceKotoolsAssert

private const val NEW_PACKAGE: String = "kotools.assert"

/** Asserts that the current value is equal to the [other] value. */
@Deprecated(
    "Use the $NEW_PACKAGE.assertEquals function instead.",
    ReplaceWith("this.assertEquals(other)", "$NEW_PACKAGE.assertEquals")
)
@SinceKotoolsAssert("1.0")
public infix fun <T : Any> T.assertEquals(other: T): Unit =
    kotlin.test.assertEquals(other, this)

/**
 * Asserts that the current value is equal to the [other] value, or report the
 * [message] if not.
 */
@Deprecated(
    "Use the $NEW_PACKAGE.assertEquals function instead.",
    ReplaceWith("this.assertEquals(other)", "$NEW_PACKAGE.assertEquals")
)
@SinceKotoolsAssert("1.0")
public fun <T : Any> T.assertEquals(other: T, message: String): Unit =
    kotlin.test.assertEquals(other, this, message)
