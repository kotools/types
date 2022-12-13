package kotools.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.MapSerializer
import kotools.shared.Project.Types
import kotools.shared.SinceKotools

/**
 * Representation of maps that contain at least one entry.
 *
 * @param K The type of map keys.
 * @param V The type of map values.
 */
@Serializable(NotEmptyMapSerializer::class)
@SinceKotools(Types, "3.2")
public class NotEmptyMap<K, out V>
private constructor(private val map: Map<K, V>) : Map<K, V> by map {
    internal companion object {
        infix fun <K, V> of(map: Map<K, V>): Result<NotEmptyMap<K, V>> = map
            .takeIf(Map<K, V>::isNotEmpty)
            ?.toSuccessfulResult(::NotEmptyMap)
            ?: Result.failure(
                IllegalArgumentException("Given map shouldn't be empty.")
            )
    }

    override fun toString(): String = "$map"
}

internal class NotEmptyMapSerializer<K, V>(
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>
) : Serializer<NotEmptyMap<K, V>, Map<K, V>>(
    delegate = MapSerializer(keySerializer, valueSerializer),
    toDelegatedType = { it },
    toType = Map<K, V>::toNotEmptyMap
)

/**
 * Creates a [NotEmptyMap] starting with a [head] and containing all the entries
 * of the optional [tail].
 */
@SinceKotools(Types, "3.2")
public fun <K, V> notEmptyMapOf(
    head: Pair<K, V>,
    vararg tail: Pair<K, V>
): NotEmptyMap<K, V> = mapOf(head, *tail)
    .toNotEmptyMap()
    .getOrThrow()

/**
 * Returns a [NotEmptyMap] containing all the entries of this map, or an
 * [IllegalArgumentException] if this map is empty.
 */
@SinceKotools(Types, "3.2")
public fun <K, V> Map<K, V>.toNotEmptyMap(): Result<NotEmptyMap<K, V>> =
    NotEmptyMap of this
