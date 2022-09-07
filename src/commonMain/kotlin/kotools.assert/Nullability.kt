package kotools.assert

import kotlin.test.assertNotNull
import kotlin.test.assertNull

/** Asserts that this value is `null`. */
@SinceKotoolsAssert("3.0")
public fun <T> T?.assertNull(): Unit = assertNull(this)

/** Asserts that the result of calling the [block] function is `null`. */
@SinceKotoolsAssert("3.0")
public inline fun <T> assertNull(block: () -> T?): Unit = block().assertNull()

/** Asserts that this value is not `null` and returns it. */
@SinceKotoolsAssert("3.0")
public fun <T> T?.assertNotNull(): T = assertNotNull(this)

/**
 * Asserts that the result of calling the [block] function is not `null` and
 * returns it.
 */
@SinceKotoolsAssert("3.0")
public inline fun <T> assertNotNull(block: () -> T?): T =
    block().assertNotNull()
