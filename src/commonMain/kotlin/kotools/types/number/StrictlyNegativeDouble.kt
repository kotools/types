package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.ErrorMessage
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmSynthetic

/**
 * Represents a floating-point number of type [Double] that is less than zero.
 *
 * You can use the [StrictlyNegativeDouble.Companion.create] or the
 * [StrictlyNegativeDouble.Companion.createOrNull] functions for creating an
 * instance of this type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@OptIn(InternalKotoolsTypesApi::class)
public class StrictlyNegativeDouble private constructor(
    private val value: Double
) {
    /** Contains static declarations for the [StrictlyNegativeDouble] type. */
    public companion object {
        /**
         * Creates a [StrictlyNegativeDouble] from the specified [number], which
         * may involve rounding or truncation, or throws an
         * [IllegalArgumentException] if the [number] is greater than or equals
         * zero.
         *
         * <br/>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val result: Result<StrictlyNegativeDouble> = runCatching {
         *     StrictlyNegativeDouble.create(-23)
         * }
         * println(result.isSuccess) // true
         * ```
         * </details>
         *
         * <br/>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this function from Java code:
         *
         * ```java
         * // The following code prints 'Success'.
         * try {
         *     StrictlyNegativeDouble.Companion.create(-23);
         *     System.out.println("Success");
         * } catch (IllegalArgumentException exception) {
         *     final String reason = exception.getMessage();
         *     System.out.println("Failure: " + reason);
         * }
         * ```
         * </details>
         * <br/>
         *
         * You can use the [StrictlyNegativeDouble.Companion.createOrNull] for
         * returning `null` instead of throwing an exception in case of invalid
         * [number].
         */
        public fun create(number: Number): StrictlyNegativeDouble {
            val result: StrictlyNegativeDouble? = createOrNull(number)
            return requireNotNull(result) { invalid(number) }
        }

        /**
         * Creates a [StrictlyNegativeDouble] from the specified [number], which
         * may involve rounding or truncation, or returns `null` if the [number]
         * is greater than or equals zero.
         *
         * <br/>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val number: Number = Random.nextInt(Int.MIN_VALUE until 0)
         * val result: StrictlyNegativeDouble? =
         *     StrictlyNegativeDouble.createOrNull(number)
         * println(result != null) // true
         * ```
         * </details>
         *
         * <br/>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this function from Java code:
         *
         * ```java
         * final Random random = new Random();
         * final Number number = random.nextInt(Integer.MIN_VALUE, 0);
         * final StrictlyNegativeDouble result =
         *         StrictlyNegativeDouble.Companion.createOrNull(number);
         * System.out.println(result != null); // true
         * ```
         * </details>
         * <br/>
         *
         * You can use the [StrictlyNegativeDouble.Companion.create] function
         * for throwing an exception instead of returning `null` in case of
         * invalid [number].
         */
        public fun createOrNull(number: Number): StrictlyNegativeDouble? {
            val value: Double = number.toDouble()
            return if (value < 0) StrictlyNegativeDouble(value) else null
        }

        @JvmSynthetic
        internal fun invalid(number: Number): ErrorMessage = ErrorMessage(
            "Number should be less than zero (tried with $number)."
        )
    }

    /**
     * Returns the string representation of this floating-point number.
     *
     * <br/>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val number: StrictlyNegativeDouble = StrictlyNegativeDouble.create(-23)
     * val result = "$number" // or number.toString()
     * println(result) // -23.0
     * ```
     * </details>
     *
     * <br/>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this function from Java code:
     *
     * ```java
     * final StrictlyNegativeDouble number =
     *         StrictlyNegativeDouble.Companion.create(-23);
     * final String result = number.toString();
     * System.out.println(result); // -23.0
     * ```
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String = "$value"
}
