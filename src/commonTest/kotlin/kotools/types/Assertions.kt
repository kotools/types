package kotools.types

import kotlin.test.*

infix fun <T> T.shouldEqual(expected: T): Unit = assertEquals(expected, this)

infix fun <T> Collection<T>.contentShouldEqual(expected: Collection<T>): Unit =
    assertContentEquals(expected, this)

infix fun <T> T.shouldNotEqual(illegal: T): Unit =
    assertNotEquals(illegal, this)

fun <T : Any> T?.shouldBeNotNull(): T = assertNotNull(this)

fun Throwable.shouldHaveAMessage(): Unit =
    assertTrue(block = assertNotNull(message)::isNotBlank)
