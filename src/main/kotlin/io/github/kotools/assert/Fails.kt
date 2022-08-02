package io.github.kotools.assert

import kotools.assert.SinceKotoolsAssert
import kotlin.test.assertFailsWith

/** Asserts that the [block] throws an exception. */
@SinceKotoolsAssert("1.1")
public inline fun assertFails(block: () -> Unit) {
    kotlin.test.assertFails(block)
}

/**
 * Asserts that the [block] throws an exception, or report the [message] if not.
 */
@SinceKotoolsAssert("1.1")
public inline fun assertFails(message: String, block: () -> Unit) {
    kotlin.test.assertFails(message, block)
}

/** Asserts that the [block] throws the exception [T]. */
@SinceKotoolsAssert("1.2")
public inline fun <reified T : Throwable> assertFailsWith(
    block: () -> Unit
): T = assertFailsWith(block = block)

/**
 * Asserts that the [block] throws the exception [T], or report the [message] if
 * not.
 */
@SinceKotoolsAssert("1.2")
public inline fun <reified T : Throwable> assertFailsWith(
    message: String,
    block: () -> Unit
): T = assertFailsWith(message, block)
