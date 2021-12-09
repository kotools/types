package com.github.kotools.csvfile

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal inline fun <T> assertNull(block: () -> T?): Unit = assertNull(block())

internal infix fun <T> T.assertEquals(other: T): Unit =
    assertEquals(other, actual = this)

internal infix fun <T> T?.assertNotEquals(other: T) {
    assertNotNull()
    this?.let { assertNotEquals(other, it) }
}

private fun <T> T?.assertNotNull() {
    assertNotNull(actual = this)
}
