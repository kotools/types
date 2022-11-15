package kotools.types.collections

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.Package
import kotools.types.SinceKotoolsTypes

// ---------- Builders ----------

/**
 * Creates a [NotEmptySet] starting with a [head] and containing all the
 * elements of the optional [tail].
 */
@SinceKotoolsTypes("3.0")
public fun <E> notEmptySetOf(head: E, vararg tail: E): NotEmptySet<E> {
    val set: Set<E> = tail.toSet()
    return notEmptySetOf(head, set)
}

private fun <E> notEmptySetOf(head: E, tail: Set<E>): NotEmptySet<E> {
    val set: Set<E> = setOf(head) + tail
    return NotEmptySetImplementation(set)
}

/**
 * Returns a [NotEmptySet] containing all the elements of this array, or throws
 * an [IllegalArgumentException] if this array is empty.
 */
@Deprecated(
    "Use the Array.toNotEmptySetOrThrow function instead.",
    ReplaceWith(
        "this.toNotEmptySetOrThrow()",
        "${Package.collections}.toNotEmptySetOrThrow"
    )
)
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public fun <E> Array<E>.toNotEmptySet(): NotEmptySet<E> = toNotEmptySetOrThrow()

/**
 * Returns a [NotEmptySet] containing all the elements of this collection, or
 * throws an [IllegalArgumentException] if this collection is empty.
 */
@Deprecated(
    "Use the Collection.toNotEmptySetOrThrow function instead.",
    ReplaceWith(
        "this.toNotEmptySetOrThrow()",
        "${Package.collections}.toNotEmptySetOrThrow"
    )
)
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public fun <E> Collection<E>.toNotEmptySet(): NotEmptySet<E> =
    toNotEmptySetOrThrow()

/**
 * Returns a [NotEmptySet] containing all the elements of this array, or returns
 * the result of calling the [defaultValue] function if this array is empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <E> Array<E>.toNotEmptySetOrElse(
    defaultValue: (Array<E>) -> NotEmptySet<E>
): NotEmptySet<E> = toNotEmptySetOrNull() ?: defaultValue(this)

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
 * `null` if this array is empty.
 */
@SinceKotoolsTypes("1.3")
public fun <E> Array<E>.toNotEmptySetOrNull(): NotEmptySet<E>? = toSet()
    .toNotEmptySetOrNull()

/**
 * Returns a [NotEmptySet] containing all the elements of this collection, or
 * returns `null` if this collection is empty.
 */
@SinceKotoolsTypes("1.3")
public fun <E> Collection<E>.toNotEmptySetOrNull(): NotEmptySet<E>? =
    takeIf(Collection<E>::isNotEmpty)?.run {
        val head: E = first()
        val tail: Set<E> = toList()
            .subList(1, size)
            .toSet()
        notEmptySetOf(head, tail)
    }

/**
 * Returns a [NotEmptySet] containing all the elements of this array, or throws
 * an [IllegalArgumentException] if this array is empty.
 */
@SinceKotoolsTypes("3.2")
@Throws(IllegalArgumentException::class)
public fun <E> Array<E>.toNotEmptySetOrThrow(): NotEmptySet<E> =
    toNotEmptySetOrNull()
        ?: throw IllegalArgumentException("Given array shouldn't be empty.")

/**
 * Returns a [NotEmptySet] containing all the elements of this collection, or
 * throws an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotoolsTypes("3.2")
@Throws(IllegalArgumentException::class)
public fun <E> Collection<E>.toNotEmptySetOrThrow(): NotEmptySet<E> =
    toNotEmptySetOrNull() ?: throw IllegalArgumentException(
        "Given collection shouldn't be empty."
    )

/**
 * Representation of sets that contain at least one element.
 *
 * @param E The type of elements contained in this set.
 */
@Serializable(NotEmptySetSerializer::class)
@SinceKotoolsTypes("1.3")
public sealed interface NotEmptySet<out E> : NotEmptyCollection<E>, Set<E>

private class NotEmptySetImplementation<out E>(private val set: Set<E>) :
    NotEmptySet<E>,
    Set<E> by set {
    override val head: E get() = set.first()

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

    override fun deserialize(decoder: Decoder): NotEmptySet<E> = delegate
        .deserialize(decoder)
        .toNotEmptySetOrThrow()
}
