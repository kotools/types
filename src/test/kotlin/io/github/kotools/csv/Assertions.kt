package io.github.kotools.csv

import kotlinx.coroutines.runBlocking
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

fun <T : Any> assertFails(block: suspend () -> T) {
    assertFails {
        runBlocking { block() }
    }
}

fun <T : Any?> assertNotNull(block: suspend () -> T?): Unit =
    runBlocking { assertNotNull(block()) }

fun <T : Any?> assertNull(block: suspend () -> T?): Unit =
    runBlocking { assertNull(block()) }

infix fun <T : Any> T.assertEquals(other: T): Unit = assertEquals(this, other)

infix fun <T : Any> T.assertNotEquals(other: T): Unit =
    assertNotEquals(this, other)
