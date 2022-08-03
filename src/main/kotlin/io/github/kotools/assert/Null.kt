@file:Suppress("DEPRECATION")

package io.github.kotools.assert

import kotools.assert.SinceKotoolsAssert

private const val NEW_PACKAGE: String = "kotools.assert"

/** Asserts that the result of [block] is `null`. */
@Deprecated(
    "Use the $NEW_PACKAGE.assertNull function instead.",
    ReplaceWith("$NEW_PACKAGE.assertNull(block)")
)
@SinceKotoolsAssert("1.0")
public inline fun <T : Any?> assertNull(block: () -> T): Unit =
    block().assertNull()

/**
 * Asserts that the result of [block] is `null`, or report the [message] if not.
 */
@Deprecated(
    "Use the $NEW_PACKAGE.assertNull function instead.",
    ReplaceWith("$NEW_PACKAGE.assertNull(block)")
)
@SinceKotoolsAssert("1.0")
public inline fun <T : Any?> assertNull(message: String, block: () -> T): Unit =
    block() assertNull message

/** Asserts that the current value is `null`. */
@Deprecated(
    "Use the $NEW_PACKAGE.assertNull function instead.",
    ReplaceWith("assertNull()", "$NEW_PACKAGE.assertNull")
)
@SinceKotoolsAssert("1.0")
public fun <T : Any?> T.assertNull(): Unit = kotlin.test.assertNull(this)

/** Asserts that the current value is `null`, or report the [message] if not. */
@Deprecated(
    "Use the $NEW_PACKAGE.assertNull function instead.",
    ReplaceWith("assertNull()", "$NEW_PACKAGE.assertNull")
)
@SinceKotoolsAssert("1.0")
public infix fun <T : Any?> T.assertNull(message: String): Unit =
    kotlin.test.assertNull(this, message)
