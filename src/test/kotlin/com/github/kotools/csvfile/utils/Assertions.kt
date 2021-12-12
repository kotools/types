package com.github.kotools.csvfile.utils

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal infix fun <T : Any?> T.assertEquals(other: T): Unit =
    assertEquals(other, actual = this)

internal fun <T : Any?> T.assertNotNull() {
    assertNotNull(actual = this)
}

internal infix fun <T : Any?> T.assertNotNullOrEquals(other: T) {
    assertNotNull()
    this?.let { assertNotEquals(other, it) }
}

internal fun <T : Any?> T.assertNull(): Unit = assertNull(actual = this)
