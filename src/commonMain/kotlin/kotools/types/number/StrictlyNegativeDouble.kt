package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesVersion

/**
 * Represents a floating-point number of type [Double] that is less than zero.
 *
 * You can use the [StrictlyNegativeDouble.Companion.createOrNull] function for
 * creating an instance of this type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@OptIn(InternalKotoolsTypesApi::class)
public class StrictlyNegativeDouble private constructor() {
    /** Contains static declarations for the [StrictlyNegativeDouble] type. */
    public companion object {
        /**
         * Creates a [StrictlyNegativeDouble] from the specified [number], which
         * may involve rounding or truncation, or returns `null` if the [number]
         * is greater than or equals zero.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val number: Number = Random.nextInt(Int.MIN_VALUE until 0)
         * val actual: StrictlyNegativeDouble? =
         *     StrictlyNegativeDouble.createOrNull(number)
         * println(actual != null) // true
         * ```
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
         * ```java
         * final Random random = new Random();
         * final Number number = random.nextInt(Integer.MIN_VALUE, 0);
         * final StrictlyNegativeDouble actual =
         *         StrictlyNegativeDouble.Companion.createOrNull(number);
         * System.out.println(actual != null); // true
         * ```
         * </details>
         */
        public fun createOrNull(number: Number): StrictlyNegativeDouble? {
            val value: Double = number.toDouble()
            return if (value < 0) StrictlyNegativeDouble() else null
        }
    }
}
