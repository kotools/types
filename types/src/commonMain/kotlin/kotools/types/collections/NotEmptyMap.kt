package kotools.types.collections

import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.strictlyPositive

// ---------- Builders ----------

/**
 * Creates a [NotEmptyMap] starting with a [head] and containing all the entries
 * of the optional [tail].
 */
@SinceKotoolsTypes("3.1")
public fun <K, V> notEmptyMapOf(
    head: Pair<K, V>,
    vararg tail: Pair<K, V>
): NotEmptyMap<K, V> = mapOf(head, *tail)
    .let(::NotEmptyMapImplementation)

/**
 * Returns a [NotEmptyMap] containing all the entries of this map, or throws
 * a [NotEmptyMap.ConstructionError] if this map is empty.
 */
@Deprecated(
    "Use the Map.toNotEmptyMapOrThrow function instead.",
    ReplaceWith(
        "this.toNotEmptyMapOrThrow()",
        "${Package.collections}.toNotEmptyMapOrThrow"
    )
)
@SinceKotoolsTypes("3.1")
@Suppress("DEPRECATION")
@Throws(NotEmptyMap.ConstructionError::class)
public fun <K, V> Map<K, V>.toNotEmptyMap(): NotEmptyMap<K, V> =
    toNotEmptyMapOrNull() ?: throw NotEmptyMap.ConstructionError

/**
 * Returns a [NotEmptyMap] containing all the entries of this map, or returns
 * `null` if this map is empty.
 */
@SinceKotoolsTypes("3.1")
public fun <K, V> Map<K, V>.toNotEmptyMapOrNull(): NotEmptyMap<K, V>? =
    takeIf(Map<K, V>::isNotEmpty)
        ?.let(::NotEmptyMapImplementation)

/**
 * Returns a [NotEmptyMap] containing all the entries of this map, or returns
 * the result of calling the [defaultValue] function if this map is empty.
 */
@SinceKotoolsTypes("3.1")
public inline fun <K, V> Map<K, V>.toNotEmptyMapOrElse(
    defaultValue: (Map<K, V>) -> NotEmptyMap<K, V>
): NotEmptyMap<K, V> = toNotEmptyMapOrNull() ?: defaultValue(this)

/**
 * Returns a [NotEmptyMap] containing all the entries of this map, or throws
 * an [IllegalArgumentException] if this map is empty.
 */
@SinceKotoolsTypes("3.2")
@Throws(IllegalArgumentException::class)
public fun <K, V> Map<K, V>.toNotEmptyMapOrThrow(): NotEmptyMap<K, V> =
    toNotEmptyMapOrNull()
        ?: throw IllegalArgumentException("Given map shouldn't be empty.")

/**
 * Representation of maps that contain at least one entry.
 *
 * @param K The type of map keys.
 * @param V The type of map values.
 */
@SinceKotoolsTypes("3.1")
public sealed interface NotEmptyMap<K, out V> : Map<K, V> {
    /** First entry of this map. */
    public val head: Pair<K, V>

    // ---------- Query operations ----------

    /** Returns the [size] of this map as a [StrictlyPositiveInt]. */
    public val typedSize: StrictlyPositiveInt get() = strictlyPositive int size

    /** Error thrown when creating a [NotEmptyMap] fails. */
    @Deprecated(
        "Use the IllegalArgumentException type instead.",
        ReplaceWith("IllegalArgumentException")
    )
    public object ConstructionError :
        IllegalArgumentException("NotEmptyMap can't be empty.")
}

private class NotEmptyMapImplementation<K, out V>(private val map: Map<K, V>) :
    NotEmptyMap<K, V>,
    Map<K, V> by map {
    override val head: Pair<K, V> = map.entries.first()
        .toPair()

    // ---------- Conversions ----------

    override fun toString(): String = map.toString()
}
