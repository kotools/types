package kotools.assert

import kotlin.test.assertFalse
import kotlin.test.assertTrue

/** Asserts that this value is `true`. */
@SinceKotoolsAssert("2.1")
public fun Boolean.assertTrue(): Unit = assertTrue(this)

/** Asserts that the result of calling the [block] function is `true`. */
@SinceKotoolsAssert("2.1")
public inline fun assertTrue(block: () -> Boolean): Unit =
    assertTrue(block = block)

/** Asserts that this value is `false`. */
@SinceKotoolsAssert("2.1")
public fun Boolean.assertFalse(): Unit = assertFalse(this)

/** Asserts that the result of calling the [block] function is `false`. */
@SinceKotoolsAssert("2.1")
public inline fun assertFalse(block: () -> Boolean): Unit =
    assertFalse(block = block)
