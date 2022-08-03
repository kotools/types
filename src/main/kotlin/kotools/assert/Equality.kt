package kotools.assert

/** Asserts that this value equals the [other] value. */
@SinceKotoolsAssert("2.1")
public infix fun <T> T.assertEquals(other: T): Unit =
    kotlin.test.assertEquals(other, this)

/** Asserts that this value is not equal to the [other] value. */
@SinceKotoolsAssert("2.1")
public infix fun <T> T.assertNotEquals(other: T): Unit =
    kotlin.test.assertNotEquals(other, this)
