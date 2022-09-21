package kotools.types.int

import kotools.types.annotations.SinceKotoolsTypes

/** Parent of classes responsible for holding the zero integer. */
@SinceKotoolsTypes("3.0")
public sealed interface ZeroInt : IntHolder {
    public companion object {
        /** Returns an instance of [ZeroInt] */
        public val instance: ZeroInt = ZeroIntImplementation
    }
}

@SinceKotoolsTypes("3.0")
internal object ZeroIntImplementation : ZeroInt,
    IntHolder by IntHolder(0)
