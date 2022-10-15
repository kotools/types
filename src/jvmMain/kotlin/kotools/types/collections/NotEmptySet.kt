package kotools.types.collections

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.SinceKotoolsTypes
import kotools.types.StabilityLevel

// ---------- Conversions ----------

/**
 * Returns a not empty set containing all the elements of this collection, or
 * throws an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotoolsTypes("1.3", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun <E> Collection<E>.toNotEmptySet(): NotEmptySet<E> {
    require(isNotEmpty()) { "Given collection shouldn't be empty." }
    val set: Set<E> = toSet()
    return NotEmptySet(set)
}

/**
 * Returns a not empty set containing all the elements of this array, or throws
 * an [IllegalArgumentException] if this array is empty.
 */
@SinceKotoolsTypes("1.3", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun <E> Array<E>.toNotEmptySet(): NotEmptySet<E> =
    toSet().toNotEmptySet()

/**
 * Returns a not empty set containing all the elements of this collection, or
 * returns `null` if this collection is empty.
 */
@SinceKotoolsTypes("1.3", StabilityLevel.Alpha)
public fun <E> Collection<E>.toNotEmptySetOrNull(): NotEmptySet<E>? = try {
    toNotEmptySet()
} catch (_: IllegalArgumentException) {
    null
}

/**
 * Returns a not empty set containing all the elements of this array, or returns
 * `null` if this array is empty.
 */
@SinceKotoolsTypes("1.3", StabilityLevel.Alpha)
public fun <E> Array<E>.toNotEmptySetOrNull(): NotEmptySet<E>? =
    toSet().toNotEmptySetOrNull()

/**
 * Returns a not empty set containing all the elements of this collection, or
 * returns the result of calling the [defaultValue] function if this collection
 * is empty.
 */
@SinceKotoolsTypes("1.3", StabilityLevel.Alpha)
public inline infix fun <E> Collection<E>.toNotEmptySetOrElse(
    defaultValue: (Collection<E>) -> NotEmptySet<E>
): NotEmptySet<E> = toNotEmptySetOrNull() ?: defaultValue(this)

/**
 * Returns a not empty set containing all the elements of this array, or returns
 * the result of calling the [defaultValue] function if this array is empty.
 */
@SinceKotoolsTypes("1.3", StabilityLevel.Alpha)
public inline infix fun <E> Array<E>.toNotEmptySetOrElse(
    defaultValue: (Array<E>) -> NotEmptySet<E>
): NotEmptySet<E> = toNotEmptySetOrNull() ?: defaultValue(this)

/**
 * Represents sets containing at least one element.
 *
 * @param E The type of elements contained in this set.
 *
 * @constructor Creates a not empty set starting with a [head] and containing
 * all the elements of the optional [tail].
 */
@Serializable(NotEmptySet.Serializer::class)
@SinceKotoolsTypes("1.3", StabilityLevel.Alpha)
public class NotEmptySet<out E> internal constructor(
    override val head: E,
    private val tail: Set<E>
) : AbstractSet<E>(), NotEmptyCollection<E> {
    public constructor(head: E, vararg tail: E) : this(
        head,
        tail.filterNot { it == head }.toSet()
    )

    @SinceKotoolsTypes("3.0", StabilityLevel.Alpha)
    internal constructor(set: Set<E>) : this(
        set.first(),
        set.filterNot { it == set.first() }.toSet()
    )

    // ---------- Query operations ----------

    override val size: Int get() = 1 + tail.size

    override fun iterator(): Iterator<E> = mutableSetOf(head).run {
        this += tail
        toSet().iterator()
    }

    // ---------- Positional access operations ----------

    @Deprecated(
        "The index should be a PositiveInt.",
        replaceWith = ReplaceWith(
            "this[PositiveInt(index)]",
            "kotools.types.number.PositiveInt"
        )
    )
    override fun get(index: Int): E = if (index == 0) head
    else tail.elementAt(index - 1)

    @SinceKotoolsTypes("3.0", StabilityLevel.Alpha)
    internal class Serializer<E>(elementSerializer: KSerializer<E>) :
        SealedNotEmptySetSerializer<E, NotEmptySet<E>>(
            elementSerializer,
            ::NotEmptySet
        )
}

@SinceKotoolsTypes("3.0", StabilityLevel.Alpha)
internal sealed class SealedNotEmptySetSerializer<E, C : Set<E>>(
    elementSerializer: KSerializer<E>,
    private val builder: (Set<E>) -> C
) : KSerializer<C> {
    private val delegate: KSerializer<Set<E>> = SetSerializer(elementSerializer)

    @ExperimentalSerializationApi
    override val descriptor: SerialDescriptor = SerialDescriptor(
        NotEmptySet::class.qualifiedName!!,
        delegate.descriptor
    )

    override fun serialize(encoder: Encoder, value: C): Unit =
        delegate.serialize(encoder, value)

    override fun deserialize(decoder: Decoder): C {
        val set: Set<E> = delegate.deserialize(decoder)
        return builder(set)
    }
}
