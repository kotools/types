package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes

/**
 * Returns this value as a [NonZeroNumber], or throws an
 * [IllegalArgumentException] if this value equals `0`.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun <T : kotlin.Number> T.toNonZeroNumber(): NonZeroNumber<T> =
    NonZeroNumber(this)

/**
 * Returns this value as a [NonZeroNumber], or returns `null` if this value
 * equals `0`.
 */
@SinceKotoolsTypes("3.0")
public fun <T : kotlin.Number> T.toNonZeroNumberOrNull(): NonZeroNumber<T>? =
    NonZeroNumber orNull this

/** Represents numeric values that don't equal `0`. */
@SinceKotoolsTypes("3.0")
public sealed interface NonZeroNumber<T : kotlin.Number> : Number<T> {
    public companion object {
        /**
         * Returns the [value] as a [NonZeroNumber], or throws an
         * [IllegalArgumentException] if the [value] equals `0`.
         */
        @Throws(IllegalArgumentException::class)
        public infix operator fun <T : kotlin.Number> invoke(
            value: T
        ): NonZeroNumber<T> {
            require(value != 0) {
                val type: String = NonZeroNumber::class.simpleName!!
                "$type doesn't accept 0."
            }
            return Implementation(value)
        }

        /**
         * Returns the [value] as a [NonZeroNumber], or returns `null` if the
         * [value] equals `0`.
         */
        public infix fun <T : kotlin.Number> orNull(
            value: T
        ): NonZeroNumber<T>? = try {
            invoke(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    private class Implementation<T : kotlin.Number>(
        override val value: T
    ) : NonZeroNumber<T>
}
