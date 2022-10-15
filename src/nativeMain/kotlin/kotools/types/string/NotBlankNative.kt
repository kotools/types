package kotools.types.string

import kotools.types.tryOrNull
import kotools.types.number.PositiveInt

/**
 * Returns the character of this value at the specified [index], or returns
 * `null` if the [index] is out of bounds.
 */
public infix fun NotBlankString.getOrNull(index: PositiveInt): Char? =
    tryOrNull { get(index) }
