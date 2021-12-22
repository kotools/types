package io.github.kotools.csv

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal inline fun <T : Any?> assertNull(block: () -> T?): Unit =
    assertNull(block())

internal infix fun <T : Any> T.assertEquals(other: T): Unit =
    assertEquals(other, this)

internal infix fun <T : Any> T.assertNotEquals(other: T): Unit =
    assertNotEquals(other, this)

internal fun <T : Any?> T.assertNotNull() {
    assertNotNull(actual = this)
}

internal infix fun <T : Any?> T.assertNotNullOrEquals(other: T) {
    assertNotNull()
    this?.let { assertNotEquals(other, it) }
}

internal fun <T : Any?> T.assertNull(): Unit = assertNull(actual = this)
