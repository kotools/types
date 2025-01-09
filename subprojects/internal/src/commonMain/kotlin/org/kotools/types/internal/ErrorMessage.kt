package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi

/** Contains a collection of error messages. */
@InternalKotoolsTypesApi
public object ErrorMessage {
    /**
     * Returns an error message indicating that the specified [number] shouldn't
     * be other than zero.
     */
    public fun invalidZero(number: Number): String =
        "'$number' shouldn't be other than zero."
}
