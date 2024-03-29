package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesVersion

/** Represents the [zero](https://en.wikipedia.org/wiki/0) number. */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@OptIn(InternalKotoolsTypesApi::class)
public object Zero {
    private const val VALUE: Byte = 0

    // ----------------------- Overrides from kotlin.Any -----------------------

    /** Returns the string representation of this number. */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String = this.VALUE.toString()
}
