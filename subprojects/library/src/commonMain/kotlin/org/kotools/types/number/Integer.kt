package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi

/**
 * Represents a mathematical integer, with exact arithmetic.
 *
 * Unlike Kotlin's built-in integer types ([Byte], [Short], [Int] and [Long]),
 * the [Integer] type provides exact arithmetic without overflow, and has
 * consistent behavior across all platforms when dividing an integer by zero.
 *
 * Designed to replace primitive integer types where correctness matters, this
 * type intentionally avoids name like `BigInteger` or `BigInt`, as its behavior
 * is part of its contract and not an implementation detail.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Motivations</b>
 * </summary>
 *
 * ### Motivations
 *
 * #### Integer overflow
 *
 * **Problem:** Adding, subtracting or multiplying Kotlin integer types can lead
 * to an overflow, which produces unexpected behavior.
 *
 * SAMPLE: org.kotools.types.number.IntegerSample.overflowProblem
 *
 * **Solution:** The [Integer] type can add, subtract or multiply integers
 * without producing an overflow.
 *
 * TODO: Add Kotlin sample
 *
 * #### Division by zero
 *
 * **Problem:** Performing division and remainder operations by zero on Kotlin
 * integer types have different behavior per platform: throw an
 * [ArithmeticException] on JVM and Native platforms, and return `0` on JS
 * platform.
 *
 * SAMPLE: org.kotools.types.number.IntegerJvmNativeSample.divisionByZeroProblem
 *
 * SAMPLE: org.kotools.types.number.IntegerJsSample.divisionByZeroProblem
 *
 * **Solution:** Division and remainder operations by zero on [Integer] type
 * throw an [ArithmeticException] on all platforms.
 *
 * TODO: Add Kotlin sample
 * </details>
 *
 * @since 5.2.0
 */
@ExperimentalKotoolsTypesApi
public class Integer private constructor()
