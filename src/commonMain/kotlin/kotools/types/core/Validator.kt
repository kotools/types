package kotools.types.core

internal fun interface Validator<T> {
    infix fun isValid(value: T): Boolean
}
