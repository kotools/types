@file:Suppress("DEPRECATION")

package io.github.kotools.assert

import kotools.assert.SinceKotoolsAssert

private const val NEW_PACKAGE: String = "kotools.assert"

/** Asserts that the result of [block] isn't `null`. */
@Deprecated(
    "Use the $NEW_PACKAGE.assertNotNull function instead.",
    ReplaceWith("$NEW_PACKAGE.assertNotNull(block)")
)
@SinceKotoolsAssert("1.0")
public inline fun <T : Any> assertNotNull(block: () -> T?): T =
    block().assertNotNull()

/**
 * Asserts that the result of [block] isn't `null`, or report the [message] if
 * not.
 */
@Deprecated(
    "Use the $NEW_PACKAGE.assertNotNull function instead.",
    ReplaceWith("$NEW_PACKAGE.assertNotNull(block)")
)
@SinceKotoolsAssert("1.0")
public inline fun <T : Any> assertNotNull(message: String, block: () -> T?): T =
    block() assertNotNull message

/** Asserts that the current value isn't `null`. */
@Deprecated(
    "Use the $NEW_PACKAGE.assertNotNull function instead.",
    ReplaceWith("assertNotNull()", "$NEW_PACKAGE.assertNotNull")
)
@SinceKotoolsAssert("1.0")
public fun <T : Any> T?.assertNotNull(): T = kotlin.test.assertNotNull(this)

/**
 * Asserts that the current value isn't `null`, or report the [message] if not.
 */
@Deprecated(
    "Use the $NEW_PACKAGE.assertNotNull function instead.",
    ReplaceWith("assertNotNull()", "$NEW_PACKAGE.assertNotNull")
)
@SinceKotoolsAssert("1.0")
public infix fun <T : Any> T?.assertNotNull(message: String): T =
    kotlin.test.assertNotNull(this, message)
