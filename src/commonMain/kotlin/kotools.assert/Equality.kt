package kotools.assert

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/** Asserts that this value equals the [other] value. */
@SinceKotoolsAssert("3.0")
public infix fun <T> T.assertEquals(other: T): Unit = assertEquals(other, this)

/** Asserts that this value is not equal to the [other] value. */
@SinceKotoolsAssert("3.0")
public infix fun <T> T.assertNotEquals(other: T): Unit =
    assertNotEquals(other, this)
