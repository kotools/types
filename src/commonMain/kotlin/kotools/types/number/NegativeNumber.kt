package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes

/**
 * Returns this value as a [NegativeNumber], or throws an
 * [IllegalArgumentException] if this value is strictly positive.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun <T : kotlin.Number> T.toNegativeNumber(): NegativeNumber<T> =
    NegativeNumber(this)

/**
 * Returns this value as a [NegativeNumber], or returns `null` if this value is
 * strictly positive.
 */
@SinceKotoolsTypes("3.0")
public fun <T : kotlin.Number> T.toNegativeNumberOrNull(): NegativeNumber<T>? =
    NegativeNumber orNull this

/** Represents negative numeric values, including `0`. */
@SinceKotoolsTypes("3.0")
public sealed interface NegativeNumber<T : kotlin.Number> : Number<T> {
    public companion object {
        /**
         * Returns the [value] as a [NegativeNumber], or throws an
         * [IllegalArgumentException] if the [value] is strictly positive.
         */
        @Throws(IllegalArgumentException::class)
        public infix operator fun <T : kotlin.Number> invoke(
            value: T
        ): NegativeNumber<T> {
            val isNegative: Boolean = value.toDouble() <= 0.toDouble()
            require(isNegative) {
                val type: String = NegativeNumber::class.simpleName!!
                val range: IntRange = Int.MIN_VALUE..0
                "$type accepts values included in $range (tried with $value)."
            }
            return Implementation(value)
        }

        /**
         * Returns the [value] as a [NegativeNumber], or returns `null` if the
         * [value] is strictly positive.
         */
        public infix fun <T : kotlin.Number> orNull(
            value: T
        ): NegativeNumber<T>? = try {
            invoke(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    private class Implementation<T : kotlin.Number>(
        override val value: T
    ) : NegativeNumber<T>
}
