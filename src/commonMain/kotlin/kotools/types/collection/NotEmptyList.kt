package kotools.types.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.ErrorMessage
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.Since
import kotools.types.internal.serializationError
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmSynthetic

/**
 * Creates a [NotEmptyList] starting with a [head] and containing all the
 * elements of the optional [tail].
 *
 * Here's a simple usage example:
 *
 * ```kotlin
 * val integers: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
 * println(integers) // [1, 2, 3]
 * ```
 */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_0_0)
public fun <E> notEmptyListOf(head: E, vararg tail: E): NotEmptyList<E> {
    val elements: List<E> = listOf(head) + tail
    return NotEmptyList.orThrow(elements)
}

/**
 * Returns an encapsulated [NotEmptyList] containing all the elements of this
 * collection, or returns an encapsulated [IllegalArgumentException] if this
 * collection is [empty][Collection.isEmpty].
 *
 * Here's a simple usage example:
 *
 * ```kotlin
 * var collection: Collection<Int> = listOf(1, 2, 3)
 * var result: Result<NotEmptyList<Int>> = collection.toNotEmptyList()
 * println(result) // Success([1, 2, 3])
 *
 * collection = emptyList()
 * result = collection.toNotEmptyList()
 * println(result) // Failure(IllegalArgumentException)
 * ```
 *
 * Please note that changes made to the original collection will not be
 * reflected on the resulting [NotEmptyList].
 *
 * ```kotlin
 * val original: MutableCollection<Int> = mutableListOf(1, 2, 3)
 * val notEmptyList: NotEmptyList<Int> = original.toNotEmptyList()
 *     .getOrThrow()
 * println(original) // [1, 2, 3]
 * println(notEmptyList) // [1, 2, 3]
 *
 * original.clear()
 * println(original) // []
 * println(notEmptyList) // [1, 2, 3]
 * ```
 */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_0_0)
public fun <E> Collection<E>.toNotEmptyList(): Result<NotEmptyList<E>> =
    runCatching {
        val elements: List<E> = toList()
        NotEmptyList.orThrow(elements)
    }

/**
 * Represents a list that has at least one element of type [E].
 *
 * You can use the [notEmptyListOf] or the [toNotEmptyList] functions for
 * building this type.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Serialization and deserialization</b>
 * </summary>
 *
 * The [serialization and deserialization processes](https://kotlinlang.org/docs/serialization.html)
 * of this type behave like for the [List] type.
 *
 * Here's an example of Kotlin code that encodes and decodes this type using the
 * [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json):
 *
 * ```kotlin
 * val string: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
 * val encoded: String = Json.encodeToString(string)
 * println(encoded) // [1,2,3]
 * val decoded: NotEmptyList<Int> = Json.decodeFromString(encoded)
 * println(decoded == string) // true
 * ```
 * </details>
 */
@JvmInline
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(NotEmptyListSerializer::class)
@Since(KotoolsTypesVersion.V4_0_0)
public value class NotEmptyList<out E> private constructor(
    private val elements: List<E>
) : NotEmptyCollection<E> {
    /** Contains static declarations for the [NotEmptyList] type. */
    public companion object {
        /**
         * Creates a [NotEmptyList] containing all the elements of the specified
         * [collection], or throws an [IllegalArgumentException] if the
         * [collection] is [empty][Collection.isEmpty].
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val collection: Collection<Int> = listOf(1, 2, 3)
         * val elements: NotEmptyList<Int> = NotEmptyList.create(collection)
         * println(elements) // [1, 2, 3]
         * ```
         *
         * The [NotEmptyList] type being an
         * [inline value class](https://kotlinlang.org/docs/inline-classes.html),
         * this function is not available yet for Java users.
         *
         * Please note that changes made to the original collection will not be
         * reflected on the resulting [NotEmptyList].
         *
         * ```kotlin
         * val original: MutableCollection<Int> = mutableListOf(1, 2, 3)
         * val notEmptyList: NotEmptyList<Int> = NotEmptyList.create(original)
         * println(original) // [1, 2, 3]
         * println(notEmptyList) // [1, 2, 3]
         *
         * original.clear()
         * println(original) // []
         * println(notEmptyList) // [1, 2, 3]
         * ```
         *
         * You can use the [NotEmptyList.Companion.createOrNull] function for
         * returning `null` instead of throwing an exception in case of invalid
         * [collection].
         */
        @ExperimentalKotoolsTypesApi
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmSynthetic
        public fun <E> create(collection: Collection<E>): NotEmptyList<E> {
            val result: NotEmptyList<E>? = createOrNull(collection)
            return requireNotNull(result) { ErrorMessage.emptyCollection }
        }

        /**
         * Creates a [NotEmptyList] containing all the elements of the specified
         * [collection], or returns `null` if the [collection] is
         * [empty][Collection.isEmpty].
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val collection: Collection<Int> = listOf(1, 2, 3)
         * val elements: NotEmptyList<Int>? =
         *     NotEmptyList.createOrNull(collection)
         * println(elements) // [1, 2, 3]
         * ```
         *
         * The [NotEmptyList] type being an
         * [inline value class](https://kotlinlang.org/docs/inline-classes.html),
         * this function is not available yet for Java users.
         *
         * Please note that changes made to the original collection will not be
         * reflected on the resulting [NotEmptyList].
         *
         * ```kotlin
         * val original: MutableCollection<Int> = mutableListOf(1, 2, 3)
         * val notEmptyList: NotEmptyList<Int>? =
         *     NotEmptyList.createOrNull(original)
         * println(original) // [1, 2, 3]
         * println(notEmptyList) // [1, 2, 3]
         *
         * original.clear()
         * println(original) // []
         * println(notEmptyList) // [1, 2, 3]
         * ```
         *
         * You can use the [NotEmptyList.Companion.create] function for throwing
         * an exception instead of returning `null` in case of invalid
         * [collection].
         */
        @ExperimentalKotoolsTypesApi
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmSynthetic
        public fun <E> createOrNull(
            collection: Collection<E>
        ): NotEmptyList<E>? {
            if (collection.isEmpty()) return null
            val elements: List<E> = collection.toList()
            return NotEmptyList(elements)
        }

        /**
         * Creates a [NotEmptyList] starting with a [head] and containing all
         * the elements of the optional [tail].
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val integers: NotEmptyList<Int> = NotEmptyList.of(1, 2, 3)
         * println(integers) // [1, 2, 3]
         * ```
         *
         * The [NotEmptyList] type being an
         * [inline value class](https://kotlinlang.org/docs/inline-classes.html),
         * this function is not available yet for Java users.
         */
        @ExperimentalKotoolsTypesApi
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmSynthetic
        public fun <E> of(head: E, vararg tail: E): NotEmptyList<E> {
            val elements: List<E> = listOf(head) + tail
            return create(elements)
        }

        @InternalKotoolsTypesApi
        @JvmSynthetic
        internal fun <E> orThrow(elements: List<E>): NotEmptyList<E> {
            val isValid: Boolean = elements.isNotEmpty()
            require(isValid, ErrorMessage.Companion::emptyCollection)
            return NotEmptyList(elements)
        }
    }

    override val head: E get() = elements.first()

    override val tail: NotEmptyList<E>?
        get() = elements.drop(1)
            .toNotEmptyList()
            .getOrNull()

    /**
     * Returns all elements of this list as a [List] of type [E].
     *
     * Here's a simple usage example:
     *
     * ```kotlin
     * val notEmptyList: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
     * val list: List<Int> = notEmptyList.toList()
     * println(list) // [1, 2, 3]
     * ```
     */
    public fun toList(): List<E> = elements

    override fun toString(): String = "$elements"
}

@InternalKotoolsTypesApi
internal class NotEmptyListSerializer<E>(elementSerializer: KSerializer<E>) :
    KSerializer<NotEmptyList<E>> {
    private val delegate: KSerializer<List<E>> by lazy {
        ListSerializer(elementSerializer)
    }

    override val descriptor: SerialDescriptor by lazy { delegate.descriptor }

    override fun serialize(encoder: Encoder, value: NotEmptyList<E>) {
        val elements: List<E> = value.toList()
        encoder.encodeSerializableValue(delegate, elements)
    }

    override fun deserialize(decoder: Decoder): NotEmptyList<E> = decoder
        .decodeSerializableValue(delegate)
        .toNotEmptyList()
        .getOrNull()
        ?: serializationError(ErrorMessage.emptyCollection)
}
