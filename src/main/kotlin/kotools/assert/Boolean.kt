package kotools.assert

/** Asserts that this value is `true`. */
@SinceKotoolsAssert("2.1")
public fun Boolean.assertTrue(): Unit = kotlin.test.assertTrue(this)

/** Asserts that the result of calling the [block] function is `true`. */
@SinceKotoolsAssert("2.1")
public inline fun assertTrue(block: () -> Boolean): Unit =
    kotlin.test.assertTrue(block = block)

/** Asserts that this value is `false`. */
@SinceKotoolsAssert("2.1")
public fun Boolean.assertFalse(): Unit = kotlin.test.assertFalse(this)

/** Asserts that the result of calling the [block] function is `false`. */
@SinceKotoolsAssert("2.1")
public inline fun assertFalse(block: () -> Boolean): Unit =
    kotlin.test.assertFalse(block = block)
