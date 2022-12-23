package kotools.types.collection

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.MapSerializer
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString
import kotools.types.toSuccessfulResult

/**
 * Representation of maps that contain at least one entry.
 *
 * @param K The type of map keys.
 * @param V The type of map values.
 */
@Serializable(NotEmptyMapSerializer::class)
@SinceKotoolsTypes("4.0")
public class NotEmptyMap<K, out V>
private constructor(private val map: Map<K, V>) : Map<K, V> by map {
    internal companion object {
        infix fun <K, V> of(map: Map<K, V>): Result<NotEmptyMap<K, V>> = map
            .takeIf(Map<K, V>::isNotEmpty)
            ?.toSuccessfulResult(::NotEmptyMap)
            ?: Result.failure(EmptyMapException)
    }

    override fun toString(): String = "$map"
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
    .toNotEmptyMap()
    .getOrThrow()

/**
 * Returns a [NotEmptyMap] containing all the entries of this map, or returns an
 * [IllegalArgumentException] if this map is empty.
 */
@SinceKotoolsTypes("4.0")
public fun <K, V> Map<K, V>.toNotEmptyMap(): Result<NotEmptyMap<K, V>> =
    NotEmptyMap of this

internal class NotEmptyMapSerializer<K, V>(
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>
) : DelegatedSerializer<Map<K, V>, NotEmptyMap<K, V>> {
    override val delegate: KSerializer<Map<K, V>> by lazy {
        MapSerializer(keySerializer, valueSerializer)
    }

    override val serialName: Result<NotBlankString> by lazy(
        "${Package.collection}.NotEmptyMap"::toNotBlankString
    )

    override val deserializationException: IllegalArgumentException by lazy {
        EmptyMapException
    }

    override fun Map<K, V>.toResultOfB(): Result<NotEmptyMap<K, V>> =
        toNotEmptyMap()
}

private object EmptyMapException :
    IllegalArgumentException("Given map shouldn't be empty.")
