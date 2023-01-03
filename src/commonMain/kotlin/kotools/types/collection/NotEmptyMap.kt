package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt
import kotools.types.toSuccessfulResult

/**
 * Representation of maps that contain at least one entry with a key of type
 * [K] and a value of type [V].
 *
 * See the [notEmptyMapOf] function or the [asNotEmptyMap] property for building
 * a [NotEmptyMap].
 */
@Serializable(NotEmptyMapSerializer::class)
@SinceKotoolsTypes("4.0")
public data class NotEmptyMap<K, out V> internal constructor(
    /** The first entry of this map. */
    public val head: Pair<K, V>,
    /** All entries of this map except [the first one][head]. */
    public val tail: NotEmptyMap<K, V>? = null
) {
    /**
     * Returns all entries of this map as a [Map] with keys of type [K] and
     * values of type [V].
     */
    public val asMap: Map<K, V> by lazy {
        tail?.let {
            val tail: Array<Pair<K, V>> = it.entries.toSet()
                .map(Map.Entry<K, V>::toPair)
                .toTypedArray()
            mapOf(head, *tail)
        } ?: mapOf(head)
    }

    /** All entries of this map. */
    public val entries: NotEmptySet<Map.Entry<K, V>> by lazy(
        asMap.entries.toNotEmptySet()::getOrThrow
    )

    /** All keys of this map. */
    public val keys: NotEmptySet<K> by lazy(
        asMap.keys.toNotEmptySet()::getOrThrow
    )

    /** All values of this map. */
    public val values: NotEmptyList<V> by lazy(
        asMap.values.toNotEmptyList()::getOrThrow
    )

    /** The size of this map. */
    public val size: StrictlyPositiveInt by lazy(
        asMap.size.toStrictlyPositiveInt()::getOrThrow
    )

    /** Returns the string representation of this map. */
    override fun toString(): String = "$asMap"
}

/**
 * Returns a [NotEmptyMap] containing all the entries of this map, or returns an
 * [IllegalArgumentException] if this map is empty.
 */
@SinceKotoolsTypes("4.0")
public val <K, V> Map<K, V>.asNotEmptyMap: Result<NotEmptyMap<K, V>>
    get() = if (isEmpty()) Result.failure(EmptyMapException)
    else entries.map(Map.Entry<K, V>::toPair).toSuccessfulResult {
        val head: Pair<K, V> = it.first()
        val tail: NotEmptyMap<K, V>? = it.drop(1)
            .toMap()
            .asNotEmptyMap
            .getOrNull()
        NotEmptyMap(head, tail)
    }

/**
 * Creates a [NotEmptyMap] starting with a [head] and containing all the entries
 * of the optional [tail].
 */
@SinceKotoolsTypes("4.0")
public fun <K, V> notEmptyMapOf(
    head: Pair<K, V>,
    vararg tail: Pair<K, V>
): NotEmptyMap<K, V> = mapOf(head, *tail)
    .asNotEmptyMap
    .getOrThrow()

internal class NotEmptyMapSerializer<K, V>(
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>
) : KSerializer<NotEmptyMap<K, V>> {
    private val delegate: KSerializer<Map<K, V>> by lazy {
        MapSerializer(keySerializer, valueSerializer)
    }

    @ExperimentalSerializationApi
    override val descriptor: SerialDescriptor by lazy {
        SerialDescriptor(
            "${Package.collection}.NotEmptyMap",
            delegate.descriptor
        )
    }

    override fun serialize(encoder: Encoder, value: NotEmptyMap<K, V>): Unit =
        encoder.encodeSerializableValue(delegate, value.asMap)

    override fun deserialize(decoder: Decoder): NotEmptyMap<K, V> = decoder
        .decodeSerializableValue(delegate)
        .asNotEmptyMap
        .getOrNull()
        ?: throw SerializationException(EmptyMapException)
}

private object EmptyMapException :
    IllegalArgumentException("Given map shouldn't be empty.")
