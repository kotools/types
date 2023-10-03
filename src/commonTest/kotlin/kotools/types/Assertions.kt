package kotools.types

import kotlinx.serialization.SerializationException
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

infix fun <T> T.shouldEqual(expected: T): Unit = assertEquals(expected, this)

inline fun <T> T.shouldEqual(expected: () -> T) {
    val expectedValue: T = expected()
    assertEquals(expectedValue, this)
}

infix fun <T> T.shouldNotEqual(illegal: T): Unit =
    assertNotEquals(illegal, this)

infix fun <T> Collection<T>.contentShouldEqual(expected: Collection<T>): Unit =
    assertContentEquals(expected, this)

fun <T : Any> T?.shouldBeNotNull(): T = assertNotNull(this)

fun Any?.shouldBeNull(): Unit = assertNull(this)

inline fun <T> T.shouldFailWithIllegalArgumentException(
    block: T.() -> Unit
): IllegalArgumentException = assertFailsWith { block() }

inline fun <T> T.shouldFailWithSerializationException(
    block: T.() -> Unit
): SerializationException = assertFailsWith { block() }

fun Throwable.shouldHaveAMessage(): Unit =
    assertTrue(block = assertNotNull(message)::isNotBlank)
