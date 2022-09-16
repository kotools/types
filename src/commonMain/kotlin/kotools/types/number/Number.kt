package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes

/** Returns this value as a [Number]. */
@SinceKotoolsTypes("3.0")
public fun <T : kotlin.Number> T.toKotoolsNumber(): Number<T> = Number(this)

/** Represents numeric values. */
@SinceKotoolsTypes("3.0")
public sealed interface Number<T : kotlin.Number> {
    public val value: T

    public companion object {
        /** Returns the [value] as a [Number]. */
        public infix operator fun <T : kotlin.Number> invoke(
            value: T
        ): Number<T> = Implementation(value)
    }

    private class Implementation<T : kotlin.Number>(
        override val value: T
    ) : Number<T>
}
