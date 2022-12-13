package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools

/**
 * Representation of maps that contain at least one entry.
 *
 * @param K The type of map keys.
 * @param V The type of map values.
 */
@SinceKotools(Types, "3.2")
public class NotEmptyMap<K, out V>
private constructor(private val map: Map<K, V>) : Map<K, V> by map {
    internal companion object {
        infix fun <K, V> of(map: Map<K, V>): Result<NotEmptyMap<K, V>> = map
            .takeIf(Map<K, V>::isNotEmpty)
            ?.toSuccessfulResult(::NotEmptyMap)
            ?: Result.failure(EmptyMapError)
    }

    override fun toString(): String = "$map"
}

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
