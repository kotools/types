package kotools.types.core

// ---------- Holder ----------

/**
 * Parent of classes responsible for holding values.
 *
 * @param T The type of values to hold.
 */
@SinceKotoolsTypes("3.0")
public interface Holder<out T : Any> {
    /** The value to hold. */
    public val value: T

    // ---------- Comparisons ----------

    /**
     * Returns `true` if the [other] object is an [Holder] holding the same
     * [value].
     */
    override fun equals(other: Any?): Boolean

    /** Returns the [hash code][Any.hashCode] of the [value]. */
    override fun hashCode(): Int

    // ---------- Conversions ----------

    /** Returns the string representation of the [value]. */
    override fun toString(): String
}

internal abstract class AbstractHolder<out T : Any>(override val value: T) :
    Holder<T> {
    override fun equals(other: Any?): Boolean =
        other is Holder<*> && value == other.value

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = value.toString()
}

// ---------- HolderCompanion ----------

/**
 * Parent of companion objects in subtypes of [Holder].
 *
 * @param T The type of values to hold.
 * @param R The type of [Holder] to return.
 */
@SinceKotoolsTypes("3.0")
public abstract class HolderCompanion<T : Any, out R : Holder<T>>(
    protected val builder: (T) -> R
) {
    /** Returns the [value] as a type [R], or returns `null` if it fails. */
    public infix fun orNull(value: T): R? = tryOrNull { builder(value) }
}
