package kotools.types.collection

import kotools.types.SinceKotoolsTypes
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt

/**
 * Representation of collections containing at least one element of type [E].
 */
@SinceKotoolsTypes("4.1")
public sealed interface NotEmptyCollection<out E> {
    /** The first element of this collection. */
    public val head: E

    /** All elements of this collection except the [first one][head]. */
    public val tail: NotEmptyCollection<E>? get() = null

    /** The size of this collection. */
    public val size: StrictlyPositiveInt
        get() {
            val result: Result<StrictlyPositiveInt> = tail?.size
                ?.toInt()
                ?.plus(1)
                ?.toStrictlyPositiveInt()
                ?: 1.toStrictlyPositiveInt()
            return result.getOrThrow()
        }

    /** Returns the string representation of this collection. */
    override fun toString(): String
}

internal object EmptyCollectionException :
    IllegalArgumentException("Given collection shouldn't be empty.")
