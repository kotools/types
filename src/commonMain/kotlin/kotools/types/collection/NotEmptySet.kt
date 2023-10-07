package kotools.types.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.ExperimentalSinceKotoolsTypes
import kotools.types.SinceKotoolsTypes
import kotools.types.experimental.ExperimentalCollectionApi
import kotlin.jvm.JvmInline

/**
 * Creates a [NotEmptySet] starting with a [head] and containing all the
 * elements of the optional [tail].
 *
 * Here's a simple usage example:
 *
 * ```kotlin
 * val integers: NotEmptySet<Int> = notEmptySetOf(1, 2, 3, 1)
 * println(integers) // [1, 2, 3]
 * ```
 */
@SinceKotoolsTypes("4.0")
public fun <E> notEmptySetOf(head: E, vararg tail: E): NotEmptySet<E> {
    val elements: Set<E> = setOf(head) + tail
    return NotEmptySet(elements)
}

/**
 * Returns an encapsulated [NotEmptySet] containing all the elements of this
 * collection, or returns an encapsulated [IllegalArgumentException] if this
 * collection is [empty][Collection.isEmpty].
 *
 * Here's a simple usage example:
 *
 * ```kotlin
 * var collection: Collection<Int> = setOf(1, 2, 3, 1)
 * var result: Result<NotEmptySet<Int>> = collection.toNotEmptySet()
 * println(result) // Success([1, 2, 3])
 *
 * collection = emptySet()
 * result = collection.toNotEmptySet()
 * println(result) // Failure(IllegalArgumentException)
 * ```
 *
 * Please note that changes made to the original collection will not be
 * reflected on the resulting [NotEmptySet].
 *
 * ```kotlin
 * val original: MutableCollection<Int> = mutableSetOf(1, 2, 3)
 * val notEmptySet: NotEmptySet<Int> = original.toNotEmptySet()
 *     .getOrThrow()
 * println(original) // [1, 2, 3]
 * println(notEmptySet) // [1, 2, 3]
 *
 * original.clear()
 * println(original) // []
 * println(notEmptySet) // [1, 2, 3]
 * ```
 */
@SinceKotoolsTypes("4.0")
public fun <E> Collection<E>.toNotEmptySet(): Result<NotEmptySet<E>> =
    runCatching {
        val elements: Set<E> = toSet()
        NotEmptySet(elements)
    }

/**
 * Returns a [NotEmptySet] containing all the elements of this collection, or
 * returns `null` if this collection is [empty][Collection.isEmpty].
 *
 * ```kotlin
 * var collection: Collection<Int> = setOf(1, 2, 3, 1)
 * var result: NotEmptySet<Int>? = collection.toNotEmptySetOrNull()
 * println(result) // [1, 2, 3]
 *
 * collection = emptySet()
 * result = collection.toNotEmptySetOrNull()
 * println(result) // null
 * ```
 *
 * Please note that changes made to the original collection will not be
 * reflected on the resulting [NotEmptySet].
 *
 * ```kotlin
 * val original: MutableCollection<Int> = mutableSetOf(1, 2, 3, 1)
 * val notEmptySet: NotEmptySet<Int>? = original.toNotEmptySetOrNull()
 * println(original) // [1, 2, 3]
 * println(notEmptySet) // [1, 2, 3]
 *
 * original.clear()
 * println(original) // []
 * println(notEmptySet) // [1, 2, 3]
 * ```
 *
 * You can use the [toNotEmptySetOrThrow] function for throwing an
 * [IllegalArgumentException] instead of returning `null` when this collection
 * is [empty][Collection.isEmpty].
 */
@ExperimentalCollectionApi
@ExperimentalSinceKotoolsTypes("4.3.1")
public fun <E> Collection<E>.toNotEmptySetOrNull(): NotEmptySet<E>? =
    NotEmptySet.of(this)

/**
 * Returns a [NotEmptySet] containing all the elements of this collection, or
 * throws an [IllegalArgumentException] if this collection is
 * [empty][Collection.isEmpty].
 *
 * ```kotlin
 * var collection: Collection<Int> = setOf(1, 2, 3, 1)
 * var result: NotEmptySet<Int> = collection.toNotEmptySetOrThrow()
 * println(result) // [1, 2, 3]
 *
 * collection = emptySet()
 * collection.toNotEmptySetOrThrow() // IllegalArgumentException
 * ```
 *
 * Please note that changes made to the original collection will not be
 * reflected on the resulting [NotEmptyList].
 *
 * ```kotlin
 * val original: MutableCollection<Int> = mutableSetOf(1, 2, 3, 1)
 * val notEmptySet: NotEmptySet<Int> = original.toNotEmptySetOrThrow()
 * println(original) // [1, 2, 3]
 * println(notEmptySet) // [1, 2, 3]
 *
 * original.clear()
 * println(original) // []
 * println(notEmptySet) // [1, 2, 3]
 * ```
 *
 * You can use the [toNotEmptySetOrNull] function for returning `null` instead
 * of throwing an [IllegalArgumentException] when this collection is
 * [empty][Collection.isEmpty].
 */
@ExperimentalCollectionApi
@ExperimentalSinceKotoolsTypes("4.3.1")
public fun <E> Collection<E>.toNotEmptySetOrThrow(): NotEmptySet<E> {
    val elements: NotEmptySet<E>? = NotEmptySet.of(this)
    return requireNotNull(elements) { EmptyCollectionException.message }
}

/**
 * Represents a set with at least one element of type [E].
 *
 * You can use the [notEmptySetOf] or the [toNotEmptySet] functions for building
 * this type.
 */
@JvmInline
@Serializable(NotEmptySetSerializer::class)
@SinceKotoolsTypes("4.0")
public value class NotEmptySet<out E> internal constructor(
    private val elements: Set<E>
) : NotEmptyCollection<E> {
    override val head: E get() = elements.first()

    override val tail: NotEmptySet<E>?
        get() = elements.drop(1)
            .toNotEmptySet()
            .getOrNull()

    init {
        val isValid: Boolean = elements.isNotEmpty()
        require(isValid) { EmptyCollectionException.message }
    }

    /**
     * Returns all elements of this set as a [Set] of type [E].
     *
     * Here's a simple usage example:
     *
     * ```kotlin
     * val notEmptySet: NotEmptySet<Int> = notEmptySetOf(1, 2, 3, 1)
     * val set: Set<Int> = notEmptySet.toSet()
     * println(set) // [1, 2, 3]
     * ```
     */
    public fun toSet(): Set<E> = elements

    override fun toString(): String = "$elements"

    /** Contains static declarations for the [NotEmptySet] type. */
    public companion object {
        /**
         * Returns a [NotEmptySet] containing all the elements of the given
         * [collection], or returns `null` if the [collection] is
         * [empty][Collection.isEmpty].
         *
         * ```kotlin
         * var collection: Collection<Int> = setOf(1, 2, 3, 1)
         * var result: NotEmptySet<Int>? = NotEmptySet.of(collection)
         * println(result) // [1, 2, 3]
         *
         * collection = emptySet()
         * result = NotEmptySet.of(collection)
         * println(result) // null
         * ```
         *
         * Please note that changes made to the original [collection] will not
         * be reflected on the resulting [NotEmptySet].
         *
         * ```kotlin
         * val original: MutableCollection<Int> = mutableSetOf(1, 2, 3, 1)
         * val notEmptySet: NotEmptySet<Int>? = NotEmptySet.of(original)
         * println(original) // [1, 2, 3]
         * println(notEmptySet) // [1, 2, 3]
         *
         * original.clear()
         * println(original) // []
         * println(notEmptySet) // [1, 2, 3]
         * ```
         */
        @ExperimentalCollectionApi
        @ExperimentalSinceKotoolsTypes("4.3.2")
        public fun <E> of(collection: Collection<E>): NotEmptySet<E>? =
            collection.takeIf { it.isNotEmpty() }
                ?.toSet()
                ?.let { NotEmptySet(it) }
    }
}

internal class NotEmptySetSerializer<E>(elementSerializer: KSerializer<E>) :
    KSerializer<NotEmptySet<E>> {
    private val delegate: KSerializer<Set<E>> by lazy {
        SetSerializer(elementSerializer)
    }

    override val descriptor: SerialDescriptor by lazy { delegate.descriptor }

    override fun serialize(encoder: Encoder, value: NotEmptySet<E>) {
        val elements: Set<E> = value.toSet()
        encoder.encodeSerializableValue(delegate, elements)
    }

    override fun deserialize(decoder: Decoder): NotEmptySet<E> = decoder
        .decodeSerializableValue(delegate)
        .toNotEmptySet()
        .getOrNull()
        ?: throw SerializationException(EmptyCollectionException)
}
