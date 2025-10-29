package org.kotools.types.internal

/** Contains internal declarations for the `org.kotools.types.Integer` type. */
@InternalKotoolsTypesApi
public object Integers

/** Returns the sum of [x] and [y] integers. */
@InternalKotoolsTypesApi
public expect fun Integers.addition(x: String, y: String): String

/** Returns the difference between [x] and [y] integers. */
@InternalKotoolsTypesApi
public expect fun Integers.subtraction(x: String, y: String): String
