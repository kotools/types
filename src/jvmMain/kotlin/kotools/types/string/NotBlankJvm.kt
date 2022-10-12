package kotools.types.string

import kotools.types.SinceKotoolsTypes
import kotools.types.core.tryOrNull
import kotools.types.PositiveInt
import kotools.types.StabilityLevel

/**
 * Returns the character of this value at the specified [index], or returns
 * `null` if the [index] is out of bounds.
 */
@SinceKotoolsTypes("3.0", StabilityLevel.Alpha)
public infix fun NotBlankString.getOrNull(index: PositiveInt): Char? =
    tryOrNull { get(index) }
