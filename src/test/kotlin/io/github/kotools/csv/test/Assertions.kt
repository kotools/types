package io.github.kotools.csv.test

import kotlin.test.assertFails
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

inline fun assertFails(block: () -> Unit) {
    assertFails(block)
}

inline fun <T : Any?> assertNotNull(block: () -> T?) {
    assertNotNull(block())
}

inline fun <T : Any?> assertNull(block: () -> T?): Unit = assertNull(block())

infix fun <T : Any> T.assertNotEquals(other: T): Unit =
    assertNotEquals(this, other)
