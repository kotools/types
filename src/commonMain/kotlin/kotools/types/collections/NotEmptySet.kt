package kotools.types.collections

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.SinceKotoolsTypes

// ---------- Builders ----------

/**
 * Creates a [NotEmptySet] starting with a [head] and containing all the
 * elements of the optional [tail].
 */
@Deprecated(
    "Use the notEmptySetOf function instead.",
    ReplaceWith(
        "notEmptySetOf<E>(head, *tail)",
        "kotools.types.collections.notEmptySetOf"
    )
)
@SinceKotoolsTypes("1.3")
@Suppress("FunctionName")
public fun <E> NotEmptySet(head: E, vararg tail: E): NotEmptySet<E> {
    val set: Set<E> = tail.toSet()
    return NotEmptySet(head, set)
}

@Suppress("FunctionName")
private fun <E> NotEmptySet(head: E, tail: Set<E>): NotEmptySet<E> {
    val set: Set<E> = setOf(head) + tail
    return NotEmptySet(set)
}

/**
 * Creates a [NotEmptySet] starting with a [head] and containing all the
 * elements of the optional [tail].
 */
@SinceKotoolsTypes("3.0")
public fun <E> notEmptySetOf(head: E, vararg tail: E): NotEmptySet<E> =
    NotEmptySet(head, tail.toSet())

/**
 * Returns a [NotEmptySet] containing all the elements of this collection, or
 * throws an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public fun <E> Collection<E>.toNotEmptySet(): NotEmptySet<E> =
    toNotEmptySetOrNull() ?: throw IllegalArgumentException(
        "Given collection shouldn't be empty."
    )

/**
 * Returns a [NotEmptySet] containing all the elements of this array, or throws
 * an [IllegalArgumentException] if this array is empty.
 */
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public fun <E> Array<E>.toNotEmptySet(): NotEmptySet<E> =
    toSet().toNotEmptySet()

/**
 * Returns a [NotEmptySet] containing all the elements of this collection, or
 * returns `null` if this collection is empty.
 */
@SinceKotoolsTypes("1.3")
public fun <E> Collection<E>.toNotEmptySetOrNull(): NotEmptySet<E>? =
    takeIf { isNotEmpty() }
        ?.run {
            val head: E = first()
            val tail: Set<E> = toList()
                .subList(1, size)
                .toSet()
            NotEmptySet(head, tail)
        }

/**
 * Returns a [NotEmptySet] containing all the elements of this array, or returns
 * `null` if this array is empty.
 */
@SinceKotoolsTypes("1.3")
public fun <E> Array<E>.toNotEmptySetOrNull(): NotEmptySet<E>? =
    toSet().toNotEmptySetOrNull()

/**
 * Returns a [NotEmptySet] containing all the elements of this collection, or
 * returns the result of calling the [defaultValue] function if this collection
 * is empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <E> Collection<E>.toNotEmptySetOrElse(
    defaultValue: (Collection<E>) -> NotEmptySet<E>
): NotEmptySet<E> = toNotEmptySetOrNull() ?: defaultValue(this)

/**
 * Returns a [NotEmptySet] containing all the elements of this array, or returns
 * the result of calling the [defaultValue] function if this array is empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <E> Array<E>.toNotEmptySetOrElse(
    defaultValue: (Array<E>) -> NotEmptySet<E>
): NotEmptySet<E> = toNotEmptySetOrNull() ?: defaultValue(this)

/**
 * Representation of sets that contain at least one element.
 *
 * @param E The type of elements contained in this set.
 */
@Serializable(NotEmptySetSerializer::class)
@SinceKotoolsTypes("1.3")
public class NotEmptySet<out E> internal constructor(private val set: Set<E>) :
    Set<E> by set,
    NotEmptyCollection<E> {
    override val head: E get() = set.first()

    // ---------- Positional access operations ----------

    @Deprecated(
        "The index should be a PositiveInt.",
        replaceWith = ReplaceWith(
            "this[PositiveInt(index)]",
            "kotools.types.number.PositiveInt"
        )
    )
    override fun get(index: Int): E = elementAt(index)

    // ---------- Conversions ----------

    override fun toString(): String = set.toString()
}

internal open class NotEmptySetSerializer<E>(
    elementSerializer: KSerializer<E>
) : KSerializer<NotEmptySet<E>> {
    private val delegate: KSerializer<Set<E>> = SetSerializer(elementSerializer)
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: NotEmptySet<E>): Unit =
        delegate.serialize(encoder, value)

    override fun deserialize(decoder: Decoder): NotEmptySet<E> =
        delegate.deserialize(decoder)
            .toNotEmptySet()
}
