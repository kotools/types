package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes

/**
 * Returns this value as a [PositiveNumber], or throws
 * [IllegalArgumentException] if this value is strictly negative.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun <T : kotlin.Number> T.toPositiveNumber(): PositiveNumber<T> =
    PositiveNumber(this)

/**
 * Returns this value as a [PositiveNumber], or returns `null` if this value is
 * strictly negative.
 */
@SinceKotoolsTypes("3.0")
public fun <T : kotlin.Number> T.toPositiveNumberOrNull(): PositiveNumber<T>? =
    PositiveNumber orNull this

/** Represents numeric values that are positive, including `0`. */
@SinceKotoolsTypes("3.0")
public sealed interface PositiveNumber<T : kotlin.Number> : Number<T> {
    public companion object {
        /**
         * Returns the [value] as a [PositiveNumber], or throws an
         * [IllegalArgumentException] if the [value] is strictly negative.
         */
        @Throws(IllegalArgumentException::class)
        public infix operator fun <T : kotlin.Number> invoke(
            value: T
        ): PositiveNumber<T> {
            val isPositive: Boolean = value.toDouble() >= 0.toDouble()
            require(isPositive) {
                val type: String = PositiveNumber::class.simpleName!!
                val range: IntRange = 0..Int.MAX_VALUE
                "$type accepts values included in $range (tried with $value)."
            }
            return Implementation(value)
        }

        /**
         * Returns the [value] as a [PositiveNumber], or returns `null` if the
         * [value] is strictly negative.
         */
        public infix fun <T : kotlin.Number> orNull(
            value: T
        ): PositiveNumber<T>? = try {
            invoke(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    private class Implementation<T : kotlin.Number>(
        override val value: T
    ) : PositiveNumber<T>
}
