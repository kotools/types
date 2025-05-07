package org.kotools.types.kotlinx.serialization.internal

import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.PositiveInteger

@ExperimentalKotoolsTypesApi
internal class PositiveIntegerAsStringSerializer :
    StringSerializer<PositiveInteger> {
    // -------------------- Structural equality operations ---------------------

    override fun equals(other: Any?): Boolean =
        other is PositiveIntegerAsStringSerializer

    override fun hashCode(): Int = hashCodeOf("$this")

    // ----------------------- Serialization operations ------------------------

    override fun deserialize(text: String): PositiveInteger =
        PositiveInteger.orThrow(text)

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = simpleNameOf(this::class)
}
