package org.kotools.types.kotlinx.serialization.internal

import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero

@ExperimentalKotoolsTypesApi
internal class ZeroAsStringSerializer : StringSerializer<Zero> {
    // -------------------- Structural equality operations ---------------------

    override fun equals(other: Any?): Boolean = other is ZeroAsStringSerializer

    override fun hashCode(): Int = hashCodeOf("$this")

    // ----------------------- Serialization operations ------------------------

    override fun stringToType(text: String): Zero = Zero.orThrow(text)

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = simpleNameOf(this::class)
}
