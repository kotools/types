package kotools.types.int

import kotools.types.annotations.SinceKotoolsTypes

@SinceKotoolsTypes("3.0")
internal fun interface IntValidator {
    infix fun isValid(value: Int): Boolean
}
