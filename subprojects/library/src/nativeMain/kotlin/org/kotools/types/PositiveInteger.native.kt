package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion

/**
 * Throws a [NotImplementedError] indicating that this function is not yet
 * supported on Kotlin/Native.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_2)
public actual fun Long.toPositiveInteger(): PositiveInteger =
    TODO("Not yet supported on Kotlin/Native.")
