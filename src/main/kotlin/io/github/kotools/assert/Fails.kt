package io.github.kotools.assert

import kotlin.test.assertFailsWith

/** Asserts that the [block] throws an exception. */
public inline fun assertFails(block: () -> Unit) {
    kotlin.test.assertFails(block)
}

/** Asserts that the [block] throws the exception [T]. */
public inline fun <reified T : Throwable> assertFails(block: () -> Unit): T =
    assertFailsWith(block = block)

/**
 * Asserts that the [block] throws an exception, or report the [message] if not.
 */
public inline fun assertFails(message: String, block: () -> Unit) {
    kotlin.test.assertFails(message, block)
}

/**
 * Asserts that the [block] throws the exception [T], or report the [message] if
 * not.
 */
public inline fun <reified T : Throwable> assertFails(
    message: String,
    block: () -> Unit
): T = assertFailsWith(message, block)
