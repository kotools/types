package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion

/**
 * Throws a [NotImplementedError] indicating that this function is not yet
 * supported on Kotlin/Native.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public actual fun Integer(number: Long): Integer =
    throw NotImplementedError("Not yet supported on Kotlin/Native.")
