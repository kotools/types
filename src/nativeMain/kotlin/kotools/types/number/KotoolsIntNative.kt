package kotools.types.number

import kotools.types.core.SinceKotoolsTypes

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 * Throws an [ArithmeticException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-arithmetic-exception/index.html)
 * if the [other] value equals `0`.
 */
@SinceKotoolsTypes("3.0")
@Throws(ArithmeticException::class)
public actual infix operator fun Int.div(other: KotoolsInt): Int =
    div(other.value)

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 * Throws an [ArithmeticException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-arithmetic-exception/index.html)
 * if the [other] value equals `0`.
 */
@SinceKotoolsTypes("3.0")
@Throws(ArithmeticException::class)
public actual infix operator fun KotoolsInt.div(other: Int): Int = value / other

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to `0`.
 * Throws an [ArithmeticException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-arithmetic-exception/index.html)
 * if the [other] value equals `0`.
 */
@SinceKotoolsTypes("3.0")
@Throws(ArithmeticException::class)
public actual infix operator fun KotoolsInt.div(other: KotoolsInt): Int =
    div(other.value)
