package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools

/** Representation of explicit integers. */
@SinceKotools(Types, "3.2")
public sealed interface ExplicitInt {
    /** Returns this value as an [Int]. */
    public fun toInt(): Int

    /** Returns this value as a [String]. */
    override fun toString(): String
}
