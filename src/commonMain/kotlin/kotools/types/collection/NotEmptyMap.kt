package kotools.types.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.SinceKotoolsTypes
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt
import kotlin.jvm.JvmInline

/**
 * Creates a [NotEmptyMap] starting with a [head] and containing all the entries
 * of the optional [tail].
 *
 * ```kotlin
 * import kotools.types.collection.NotEmptyMap
 * import kotools.types.collection.notEmptyMapOf
 *
 * val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
 * println(map) // {a=1, b=2}
 * ```
 */
@SinceKotoolsTypes("4.0")
public fun <K, V> notEmptyMapOf(
    head: Pair<K, V>,
    vararg tail: Pair<K, V>
): NotEmptyMap<K, V> = mapOf(head, *tail)
    .toNotEmptyMap()
    .getOrThrow()

/**
 * Returns an encapsulated [NotEmptyMap] containing all the entries of this map,
 * or returns an encapsulated [IllegalArgumentException] if this map is
 * [empty][Map.isEmpty].
 *
 * ```kotlin
 * import kotools.types.collection.NotEmptyMap
 * import kotools.types.collection.toNotEmptyMap
 *
 * var map: Map<Char, Int> = mapOf('a' to 1, 'b' to 2)
 * var result: Result<NotEmptyMap<Char, Int>> = map.toNotEmptyMap()
 * println(result) // Success({a=1, b=2})
 *
 * map = emptyMap()
 * result = map.toNotEmptyMap()
 * println(result) // Failure(IllegalArgumentException)
 * ```
 */
@SinceKotoolsTypes("4.0")
public fun <K, V> Map<K, V>.toNotEmptyMap(): Result<NotEmptyMap<K, V>> =
    takeIf(Map<K, V>::isNotEmpty)
        ?.entries
        ?.map(Map.Entry<K, V>::toPair)
        ?.runCatching {
            val head: Pair<K, V> = first()
            val tail: NotEmptyMap<K, V>? = drop(1)
                .toMap()
                .toNotEmptyMap()
                .getOrNull()
            NotEmptyMap(head, tail)
        }
        ?: Result.failure(EmptyMapException)

/**
 * Represents a map with at least one entry with a key of type [K] and a value
 * of type [V].
 *
 * You can use the [notEmptyMapOf] or the [toNotEmptyMap] functions for building
 * this type.
 */
@JvmInline
@Serializable(NotEmptyMapSerializer::class)
@SinceKotoolsTypes("4.0")
public value class NotEmptyMap<K, out V> private constructor(
    private val map: Map<K, V>
) {
    /**
     * The first entry of this map.
     *
     * ```kotlin
     * import kotools.types.collection.NotEmptyMap
     * import kotools.types.collection.notEmptyMapOf
     *
     * val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
     * val head: Pair<Char, Int> = map.head
     * println(head) // (a, 1)
     * ```
     */
    public val head: Pair<K, V>
        get() = map.entries.first()
            .toPair()

    /**
     * All entries of this map except [the first one][head].
     *
     * ```kotlin
     * import kotools.types.collection.NotEmptyMap
     * import kotools.types.collection.notEmptyMapOf
     *
     * val map: NotEmptyMap<Char, Int> =
     *     notEmptyMapOf('a' to 1, 'b' to 2, 'c' to 3)
     * println(map.tail) // {b=2, c=3}
     * ```
     */
    public val tail: NotEmptyMap<K, V>?
        get() = map.entries.drop(1)
            .takeIf { it.isNotEmpty() }
            ?.associate { it.toPair() }
            ?.toNotEmptyMap()
            ?.getOrNull()

    /**
     * All entries of this map.
     *
     * ```kotlin
     * import kotools.types.collection.NotEmptyMap
     * import kotools.types.collection.NotEmptySet
     * import kotools.types.collection.notEmptyMapOf
     *
     * val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
     * val entries: NotEmptySet<Map.Entry<Char, Int>> = map.entries
     * println(entries) // [a=1, b=2]
     * ```
     */
    public val entries: NotEmptySet<Map.Entry<K, V>>
        get() = map.entries.toNotEmptySet()
            .getOrThrow()

    /** All keys of this map. */
    public val keys: NotEmptySet<K>
        get() = map.keys.toNotEmptySet()
            .getOrThrow()

    /** All values of this map. */
    public val values: NotEmptyList<V>
        get() = map.values.toNotEmptyList()
            .getOrThrow()

    /** The size of this map. */
    public val size: StrictlyPositiveInt
        get() = map.size.toStrictlyPositiveInt()
            .getOrThrow()

    internal constructor(
        head: Pair<K, V>,
        tail: NotEmptyMap<K, V>? = null
    ) : this(
        tail?.run { mapOf(head) + map }
            ?: mapOf(head)
    )

    /**
     * Returns all entries of this map as a [Map] with keys of type [K] and
     * values of type [V].
     */
    public fun toMap(): Map<K, V> = tail?.let {
        val tail: Array<Pair<K, V>> = it.entries.toSet()
            .map(Map.Entry<K, V>::toPair)
            .toTypedArray()
        mapOf(head, *tail)
    } ?: mapOf(head)

    /** Returns the string representation of this map. */
    override fun toString(): String = "${toMap()}"
}

internal class NotEmptyMapSerializer<K, V>(
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>
) : KSerializer<NotEmptyMap<K, V>> {
    private val delegate: KSerializer<Map<K, V>> by lazy {
        MapSerializer(keySerializer, valueSerializer)
    }

    override val descriptor: SerialDescriptor by lazy(delegate::descriptor)

    override fun serialize(encoder: Encoder, value: NotEmptyMap<K, V>): Unit =
        encoder.encodeSerializableValue(delegate, value.toMap())

    override fun deserialize(decoder: Decoder): NotEmptyMap<K, V> = decoder
        .decodeSerializableValue(delegate)
        .toNotEmptyMap()
        .getOrNull()
        ?: throw SerializationException(EmptyMapException)
}

private object EmptyMapException :
    IllegalArgumentException("Given map shouldn't be empty.")
