/*
 * Copyright 2022-2023 Loïc Lamarque, Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types

import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

infix fun <T> T.shouldEqual(expected: T): Unit = assertEquals(expected, this)

infix fun <T> T.shouldNotEqual(illegal: T): Unit =
    assertNotEquals(illegal, this)

infix fun <T> Collection<T>.contentShouldEqual(expected: Collection<T>): Unit =
    assertContentEquals(expected, this)

fun <T : Any> T?.shouldBeNotNull(): T = assertNotNull(this)

fun Any?.shouldBeNull(): Unit = assertNull(this)

inline fun <T> T.shouldFailWithIllegalArgumentException(
    block: T.() -> Unit
): IllegalArgumentException = assertFailsWith { block() }

fun Throwable.shouldHaveAMessage(): Unit =
    assertTrue(block = assertNotNull(message)::isNotBlank)
