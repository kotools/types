package kotools.types.collections

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.core.SinceKotoolsTypes
import kotools.types.int.PositiveInt
import kotools.types.int.StrictlyPositiveInt
import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankString

/**
 * Returns the element at the specified [index] in this collection, or returns
 * the result of calling the [defaultValue] function if the [index] is out of
 * bounds.
 */
@SinceKotoolsTypes("1.3")
public inline fun <E> NotEmptyCollection<E>.getOrElse(
    index: Int,
    defaultValue: (Int) -> @UnsafeVariance E
): E = getOrNull(index) ?: defaultValue(index)

/**
 * Returns the element at the specified [index] in this collection, or returns
 * the result of calling the [defaultValue] function if the [index] is out of
 * bounds.
 */
@SinceKotoolsTypes("1.3")
public inline fun <E> NotEmptyCollection<E>.getOrElse(
    index: PositiveInt,
    defaultValue: (PositiveInt) -> @UnsafeVariance E
): E = getOrNull(index) ?: defaultValue(index)

/**
 * Returns the element at the specified [index] in this collection, or returns
 * the result of calling the [defaultValue] function if the [index] is out of
 * bounds.
 */
@SinceKotoolsTypes("1.3")
public inline fun <E> NotEmptyCollection<E>.getOrElse(
    index: StrictlyPositiveInt,
    defaultValue: (StrictlyPositiveInt) -> @UnsafeVariance E
): E = getOrNull(index) ?: defaultValue(index)

/**
 * Represents collections containing at least one element.
 *
 * @param E The type of elements contained in this collection.
 */
@Serializable(NotEmptyCollectionSerializer::class)
@SinceKotoolsTypes("1.3")
public sealed interface NotEmptyCollection<out E> : Collection<E> {
    /** First element of this collection. */
    public val head: E

    // ---------- Query operations ----------

    override val size: Int get() = 1

    /** Returns the [size] of this collection as a strictly positive int. */
    public val typedSize: StrictlyPositiveInt
        get() = StrictlyPositiveInt(size)

    override fun isEmpty(): Boolean = false

    // ---------- Positional access operations ----------

    /**
     * Returns the element at the specified [index] in this collection, or
     * throws an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: Int): E

    /**
     * Returns the element at the specified [index] in this collection, or
     * throws an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: PositiveInt): E = get(index.value)

    /**
     * Returns the element at the specified [index] in this collection, or
     * throws an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: StrictlyPositiveInt): E =
        get(index.value)

    /**
     * Returns the element at the specified [index] in this collection, or
     * returns `null` if the [index] is out of bounds.
     */
    public infix fun getOrNull(index: Int): E? = try {
        get(index)
    } catch (_: IndexOutOfBoundsException) {
        null
    }

    /**
     * Returns the element at the specified [index] in this collection, or
     * returns `null` if the [index] is out of bounds.
     */
    public infix fun getOrNull(index: PositiveInt): E? =
        getOrNull(index.value)

    /**
     * Returns the element at the specified [index] in this collection, or
     * returns `null` if the [index] is out of bounds.
     */
    public infix fun getOrNull(index: StrictlyPositiveInt): E? =
        getOrNull(index.value)

    // ---------- Conversions ----------

    /**
     * Returns the string representation of this collection as a not blank
     * string.
     */
    public fun toNotBlankString(): NotBlankString =
        toString().toNotBlankString()
}

internal sealed class SealedNotEmptyCollectionSerializer<E, C : NotEmptyCollection<E>>(
    elementSerializer: KSerializer<E>,
    private val builder: (Collection<E>) -> C
) : KSerializer<C> {
    private val delegate: KSerializer<List<E>> =
        ListSerializer(elementSerializer)

    @ExperimentalSerializationApi
    override val descriptor: SerialDescriptor = SerialDescriptor(
        NotEmptyCollection::class.qualifiedName!!,
        delegate.descriptor
    )

    override fun serialize(encoder: Encoder, value: C) {
        val list: List<E> = value.toList()
        delegate.serialize(encoder, list)
    }

    override fun deserialize(decoder: Decoder): C {
        val list: List<E> = delegate.deserialize(decoder)
        return builder(list)
    }
}

internal class NotEmptyCollectionSerializer<E>(
    elementSerializer: KSerializer<E>
) : SealedNotEmptyCollectionSerializer<E, NotEmptyCollection<E>>(
    elementSerializer,
    Collection<E>::toNotEmptyListJvm
)
