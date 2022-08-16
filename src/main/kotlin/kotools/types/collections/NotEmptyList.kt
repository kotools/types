package kotools.types.collections

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.annotations.SinceKotoolsTypes

// ---------- Conversions ----------

/**
 * Returns a not empty list containing all the elements of this array, or throws
 * an [IllegalArgumentException] if this array is empty.
 */
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public inline fun <reified E> Array<E>.toNotEmptyList(): NotEmptyList<E> =
    toList().toNotEmptyList()

/**
 * Returns a not empty list containing all the elements of this collection, or
 * throws an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotoolsTypes("1.3")
@Throws(IllegalArgumentException::class)
public inline fun <reified E> Collection<E>.toNotEmptyList(): NotEmptyList<E> {
    require(isNotEmpty()) { "Given collection shouldn't be empty." }
    val head: E = first()
    val list: MutableList<E> = mutableListOf()
    for (index in 1 until size) list += elementAt(index)
    val tail: Array<E> = list.toTypedArray()
    return NotEmptyList(head, *tail)
}

/**
 * Returns a not empty list containing all the elements of this array, or
 * returns `null` if this array is empty.
 */
@SinceKotoolsTypes("1.3")
public inline fun <reified E> Array<E>.toNotEmptyListOrNull():
        NotEmptyList<E>? = toList().toNotEmptyListOrNull()

/**
 * Returns a not empty list containing all the elements of this collection, or
 * returns `null` if this collection is empty.
 */
@SinceKotoolsTypes("1.3")
public inline fun <reified E> Collection<E>.toNotEmptyListOrNull():
        NotEmptyList<E>? = try {
    toNotEmptyList()
} catch (_: IllegalArgumentException) {
    null
}

/**
 * Returns a not empty list containing all the elements of this array, or
 * returns the result of calling the [defaultValue] function if this array is
 * empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <reified E> Array<E>.toNotEmptyListOrElse(
    defaultValue: (Array<E>) -> NotEmptyList<E>
): NotEmptyList<E> = toNotEmptyListOrNull() ?: defaultValue(this)

/**
 * Returns a not empty list containing all the elements of this collection, or
 * returns the result of calling the [defaultValue] function if this collection
 * is empty.
 */
@SinceKotoolsTypes("1.3")
public inline infix fun <reified E> Collection<E>.toNotEmptyListOrElse(
    defaultValue: (Collection<E>) -> NotEmptyList<E>
): NotEmptyList<E> = toNotEmptyListOrNull() ?: defaultValue(this)

/**
 * Represents lists containing at least one element.
 *
 * @param E The type of elements contained in this list.
 *
 * @constructor Creates a not empty list starting with a [head] and containing
 * all the elements of the optional [tail].
 */
@Serializable(NotEmptyList.Serializer::class)
@SinceKotoolsTypes("1.3")
public class NotEmptyList<out E> private constructor(
    override val head: E,
    private val tail: List<E>
) : AbstractList<E>(), NotEmptyCollection<E> {
    public constructor(head: E, vararg tail: E) : this(head, tail.toList())

    // ---------- Query operations ----------

    override val size: Int get() = tail.size + super.size

    override fun isEmpty(): Boolean = super<NotEmptyCollection>.isEmpty()

    // ---------- Positional access operations ----------

    override fun get(index: Int): E = if (index == 0) head else tail[index - 1]

    @SinceKotoolsTypes("2.1")
    internal class Serializer<E>(elementSerializer: KSerializer<E>) :
        KSerializer<NotEmptyList<E>> {
        private val delegate: KSerializer<List<E>> =
            ListSerializer(elementSerializer)

        @ExperimentalSerializationApi
        override val descriptor: SerialDescriptor = SerialDescriptor(
            NotEmptyList::class.qualifiedName!!,
            delegate.descriptor
        )

        override fun serialize(encoder: Encoder, value: NotEmptyList<E>): Unit =
            delegate.serialize(encoder, value)

        override fun deserialize(decoder: Decoder): NotEmptyList<E> {
            val list: List<E> = delegate.deserialize(decoder)
            val head: E = list.first()
            val tail: List<E> = list.subList(1, list.size)
            return NotEmptyList(head, tail)
        }
    }
}
