package io.github.kotools.csv.test

import kotlinx.coroutines.runBlocking
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal fun <T : Any?> assertNotNull(block: suspend () -> T?): Unit =
    runBlocking { assertNotNull(block()) }

internal fun <T : Any?> assertNull(block: suspend () -> T?): Unit =
    runBlocking { assertNull(block()) }

internal infix fun <T : Any> T.assertEquals(other: T): Unit =
    assertEquals(other, this)

internal infix fun <T : Any> T.assertNotEquals(other: T): Unit =
    assertNotEquals(other, this)
