package kotools.types

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

infix fun <T> T.shouldEqual(expected: T): Unit = assertEquals(expected, this)

infix fun <T> T.shouldNotEqual(illegal: T): Unit =
    assertNotEquals(illegal, this)

fun Throwable.assertHasAMessage(): Unit =
    assertTrue(block = assertNotNull(message)::isNotBlank)
