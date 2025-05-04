package org.kotools.types

import org.kotools.types.internal.ExceptionMessage
import kotlin.test.assertFailsWith

/**
 * Asserts that the specified [block] throws an [IllegalArgumentException], and
 * returns its message.
 */
internal inline fun assertThrowsIllegalArgumentException(
    block: () -> Unit
): ExceptionMessage = assertFailsWith<IllegalArgumentException>(block = block)
    .let(ExceptionMessage.Companion::from)

/**
 * Asserts that this message equals the result of calling the specified [block].
 */
internal inline fun ExceptionMessage.assertEquals(
    block: () -> ExceptionMessage
) {
    val expected: ExceptionMessage = block()
    kotlin.test.assertEquals(expected, actual = this)
}
