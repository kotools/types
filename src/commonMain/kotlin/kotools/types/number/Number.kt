package kotools.types.number

import kotools.types.annotations.SinceKotoolsTypes

/** Represents numeric values. */
@SinceKotoolsTypes("3.0")
public sealed interface Number<T : kotlin.Number> {
    public val value: T

    public companion object {
        /** Returns the [value] as a [Number]. */
        public infix operator fun <T : kotlin.Number> invoke(
            value: T
        ): Number<T> = NumberImplementation(value)
    }
}

@SinceKotoolsTypes("3.0")
private class NumberImplementation<T : kotlin.Number>(
    override val value: T
) : Number<T>
