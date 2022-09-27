package kotools.types.core

/** Returns the [value] as an [holderOf] of type [T]. */
@SinceKotoolsTypes("3.0")
public fun <T : Any> holderOf(value: T): Holder<T> = HolderImplementation(value)

/**
 * Parent of classes responsible for holding values.
 *
 * @param T The type of values to hold.
 */
@SinceKotoolsTypes("3.0")
public interface Holder<out T : Any> {
    /** The value to hold. */
    public val value: T
}

@SinceKotoolsTypes("3.0")
private data class HolderImplementation<out T : Any>(override val value: T) :
    Holder<T>
