package org.kotools.types.internal

/** Contains a collection of error messages. */
@InternalKotoolsTypesApi
public object ErrorMessage {
    /**
     * Returns an error message indicating that the specified [number] shouldn't
     * be other than zero.
     */
    public fun invalidZero(number: Number): String =
        "'$number' shouldn't be other than zero."

    /**
     * Returns an error message indicating that the specified [text] is not a
     * valid representation of zero.
     */
    public fun invalidZero(text: String): String =
        "'$text' is not a valid representation of zero."
}
