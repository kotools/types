package io.github.kotools.assert

import kotlin.test.assertEquals

public infix fun <T : Any> T.assertEquals(other: T): Unit =
    assertEquals(other, this)

public fun <T : Any> T.assertEquals(other: T, message: String): Unit =
    assertEquals(other, this, message)

public inline fun <T : Any> T.assertEquals(
    other: T,
    lazyMessage: () -> String
): Unit = assertEquals(other, this, lazyMessage())
