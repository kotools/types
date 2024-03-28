package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.hashCodeOf

private const val FINAL_WARNING: String = "RedundantModalityModifier"

/**
 * Represents the [zero](https://en.wikipedia.org/wiki/0) number.
 *
 * @constructor Creates an instance of [Zero].
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@OptIn(InternalKotoolsTypesApi::class)
public class Zero {
    private val value: Byte = 0

    // ----------------------- Overrides from kotlin.Any -----------------------

    /**
     * Returns `true` if the [other] object is an instance of [Zero], or returns
     * `false` otherwise.
     */
    @Suppress(FINAL_WARNING)
    final override fun equals(other: Any?): Boolean = other is Zero

    /** Returns a hash code value for this number. */
    @Suppress(FINAL_WARNING)
    final override fun hashCode(): Int = hashCodeOf(this.value)

    /** Returns the string representation of this number. */
    @Suppress(FINAL_WARNING)
    final override fun toString(): String = this.value.toString()
}
