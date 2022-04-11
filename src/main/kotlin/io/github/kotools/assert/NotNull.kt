package io.github.kotools.assert

/** Asserts that the result of [block] isn't `null`. */
public inline fun <T : Any> assertNotNull(block: () -> T?): T =
    block().assertNotNull()

/**
 * Asserts that the result of [block] isn't `null`, or report the [message] if
 * not.
 */
public inline fun <T : Any> assertNotNull(message: String, block: () -> T?): T =
    block() assertNotNull message

/** Asserts that the current value isn't `null`. */
public fun <T : Any> T?.assertNotNull(): T = kotlin.test.assertNotNull(this)

/**
 * Asserts that the current value isn't `null`, or report the [message] if not.
 */
public infix fun <T : Any> T?.assertNotNull(message: String): T =
    kotlin.test.assertNotNull(this, message)
