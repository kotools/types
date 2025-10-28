package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning

/**
 * Represents an integer that is guaranteed to be non-zero.
 *
 * Use this type to ensure compile-time safety when performing division or
 * remainder operations.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Motivations</b>
 * </summary>
 *
 * ### Division by zero
 *
 * **Problem:** Kotlin integer types ([Byte], [Short], [Int] and [Long]) allow
 * division or remainder operations with zero. These compile successfully but
 * cause inconsistent runtime behaviors across platforms:
 *
 * SAMPLE: [org.kotools.types.NonZeroIntegerJvmNativeSample.divisionProblem]
 *
 * SAMPLE: [org.kotools.types.NonZeroIntegerJsSample.divisionProblem]
 *
 * **Solution:** Use [Integer] and [NonZeroInteger] types to ensure that
 * division by zero is impossible at compile-time.
 *
 * ```kotlin
 * // 12.n / 0 doesn't compile
 * // 12.n / 0.n doesn't compile
 * assertEquals(expected = 4.n, 12.n / 3.nz)
 * assertEquals(expected = 2.n, 12.n % 5.nz)
 * ```
 *
 * See the [Integer.Literal.n] extension property for more details about
 * creating an instance of [Integer] from decimal literals.
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Declarations</b>
 * </summary>
 *
 * ### Declarations
 *
 * - **Conversions:** [toInteger][NonZeroInteger.toInteger] and
 * [toString][NonZeroInteger.toString].
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public class NonZeroInteger private constructor(private val integer: Integer) {
    /**
     * Creates an instance of [NonZeroInteger] from the specified [number], or
     * throws an [IllegalArgumentException] if the [number] is zero.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this constructor from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.NonZeroIntegerSample.constructorWithNonZeroInt]
     * </details>
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this constructor from Java code:
     *
     * SAMPLE: [org.kotools.types.NonZeroIntegerJavaSample.constructorWithNonZeroInt]
     * </details>
     */
    public constructor(number: Int) : this(Integer(number)) {
        require(number != 0) { "NonZeroInteger shouldn't be zero" }
    }

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns this integer as [Integer].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.NonZeroIntegerSample.toInteger]
     * </details>
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this function from Java code:
     *
     * SAMPLE: [org.kotools.types.NonZeroIntegerJavaSample.toInteger]
     * </details>
     */
    public fun toInteger(): Integer = this.integer

    /**
     * Returns the string representation of this integer.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.NonZeroIntegerSample.toStringOverride]
     * </details>
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this function from Java code:
     *
     * SAMPLE: [org.kotools.types.NonZeroIntegerJavaSample.toStringOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = this.integer.toString()
}
