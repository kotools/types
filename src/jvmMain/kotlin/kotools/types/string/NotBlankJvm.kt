package kotools.types.string

import kotools.types.core.SinceKotoolsTypes
import kotools.types.core.tryOrNull
import kotools.types.PositiveInt

/**
 * Returns the character of this value at the specified [index], or returns
 * `null` if the [index] is out of bounds.
 */
@SinceKotoolsTypes("3.0")
public infix fun NotBlankString.getOrNull(index: PositiveInt): Char? =
    tryOrNull { get(index) }
