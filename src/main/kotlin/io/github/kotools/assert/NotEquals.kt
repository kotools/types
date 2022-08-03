package io.github.kotools.assert

import kotools.assert.SinceKotoolsAssert

private const val NEW_PACKAGE: String = "kotools.assert"

/** Asserts that the current value isn't equal to the [other] value. */
@Deprecated(
    "Use the $NEW_PACKAGE.assertNotEquals function instead.",
    ReplaceWith("this.assertNotEquals(other)", "$NEW_PACKAGE.assertNotEquals")
)
@SinceKotoolsAssert("1.0")
public infix fun <T : Any> T.assertNotEquals(other: T): Unit =
    kotlin.test.assertNotEquals(other, this)

/**
 * Asserts that the current value isn't equal to the [other] value, or report
 * the [message] if not.
 */
@Deprecated(
    "Use the $NEW_PACKAGE.assertNotEquals function instead.",
    ReplaceWith("this.assertNotEquals(other)", "$NEW_PACKAGE.assertNotEquals")
)
@SinceKotoolsAssert("1.0")
public fun <T : Any> T.assertNotEquals(other: T, message: String): Unit =
    kotlin.test.assertNotEquals(other, this, message)
