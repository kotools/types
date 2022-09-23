package kotools.types.core

@SinceKotoolsTypes("3.0")
internal fun interface Validator<T> {
    infix fun isValid(value: T): Boolean
}
