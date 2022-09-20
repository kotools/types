package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes

/**
 * Returns this value as a [StrictlyPositiveNumber], or throws an
 * [IllegalArgumentException] if this value is negative.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun <T : kotlin.Number> T.toStrictlyPositiveNumber(): StrictlyPositiveNumber<T> =
    StrictlyPositiveNumber(this)

/**
 * Returns this value as a [StrictlyPositiveNumber], or returns `null` if this
 * value is negative.
 */
@SinceKotoolsTypes("3.0")
public fun <T : kotlin.Number> T.toStrictlyPositiveNumberOrNull(): StrictlyPositiveNumber<T>? =
    StrictlyPositiveNumber orNull this

/** Represents strictly positive numeric values, excluding `0`. */
@SinceKotoolsTypes("3.0")
public sealed interface StrictlyPositiveNumber<T : kotlin.Number> :
    PositiveNumber<T>,
    NonZeroNumber<T> {
    public companion object {
        private infix fun <T : kotlin.Number> isValid(value: T): Boolean =
            PositiveNumber isValid value && NonZeroNumber isValid value

        /**
         * Returns the [value] as a [StrictlyPositiveNumber], or throws an
         * [IllegalArgumentException] if the [value] is negative.
         */
        @Throws(IllegalArgumentException::class)
        public infix operator fun <T : kotlin.Number> invoke(
            value: T
        ): StrictlyPositiveNumber<T> {
            require(this isValid value) {
                val type: String = StrictlyPositiveNumber::class.simpleName!!
                val range: IntRange = 1..Int.MAX_VALUE
                "$type accepts values included in $range (tried with $value)."
            }
            return Implementation(value)
        }

        /**
         * Returns the [value] as a [StrictlyPositiveNumber], or returns `null`
         * if the [value] is negative.
         */
        public infix fun <T : kotlin.Number> orNull(
            value: T
        ): StrictlyPositiveNumber<T>? = try {
            invoke(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    private class Implementation<T : kotlin.Number>(
        override val value: T
    ) : StrictlyPositiveNumber<T>
}
