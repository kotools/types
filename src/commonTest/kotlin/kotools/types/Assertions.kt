package kotools.types

import kotlin.test.*

infix fun <T> T.shouldEqual(expected: T): Unit = assertEquals(expected, this)

infix fun <T> List<T>.contentShouldEqual(expected: List<T>): Unit =
    assertContentEquals(expected, this)

infix fun <T> T.shouldNotEqual(illegal: T): Unit =
    assertNotEquals(illegal, this)

fun Throwable.assertHasAMessage(): Unit =
    assertTrue(block = assertNotNull(message)::isNotBlank)
