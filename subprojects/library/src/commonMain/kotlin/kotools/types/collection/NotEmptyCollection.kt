package kotools.types.collection

import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.ZeroInt
import kotools.types.number.plus
import kotools.types.number.toStrictlyPositiveInt
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Since

/** Represents a collection that has at least one element of type [E]. */
@Since(KotoolsTypesVersion.V4_1_0)
public sealed interface NotEmptyCollection<out E> {
    /**
     * The first element of this collection.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this property from Kotlin code:
     *
     * SAMPLE: [kotools.types.collection.NotEmptyCollectionCommonSample.head]
     * </details>
     */
    public val head: E

    /**
     * All elements of this collection except the [first one][head].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this property from Kotlin code:
     *
     * SAMPLE: [kotools.types.collection.NotEmptyCollectionCommonSample.tail]
     * </details>
     */
    public val tail: NotEmptyCollection<E>? get() = null

    /** The size of this collection. */
    public val size: StrictlyPositiveInt
        get() = tail.sizeOrZero.plus(1)
            .toStrictlyPositiveInt()
            .getOrThrow()

    /**
     * Returns the string representation of this collection.
     *
     * Implementations of this function should behave like calling the
     * [Any.toString] function on an [Iterable].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: [kotools.types.collection.NotEmptyCollectionCommonSample.toStringOverride]
     * </details>
     */
    override fun toString(): String
}

/**
 * Returns the size of this collection, or returns [ZeroInt] if this collection
 * is `null`.
 */
@Since(KotoolsTypesVersion.V4_1_0)
public val NotEmptyCollection<*>?.sizeOrZero: PositiveInt
    get() = this?.size ?: ZeroInt
