package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel

/** Representation of explicit integers. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public sealed interface ExplicitInt {
    /** Returns this value as an [Int]. */
    public fun toInt(): Int
}
