package kotools.types.core

import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankString

/** Returns the [value] as an [Holder] of type [T]. */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun <T : Any> Holder(value: T): Holder<T> = HolderImplementation(value)

/** Parent of classes responsible for holding values. */
@SinceKotoolsTypes("3.0")
public interface Holder<T : Any> {
    /** The value to hold. */
    public val value: T

    /**
     * Returns the string representation of this [value] as a [NotBlankString].
     */
    public fun toNotBlankString(): NotBlankString =
        value.toString().toNotBlankString()
}

@SinceKotoolsTypes("3.0")
private data class HolderImplementation<T : Any>(override val value: T) :
    Holder<T>
