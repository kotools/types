package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion

/**
 * Represents an integer.
 *
 * Contrarily to the Kotlin integer types ([Byte], [Short], [Int] and [Long]),
 * this type has no maximum and minimum values. It can hold greater values than
 * [Long.MAX_VALUE] and lesser values than [Long.MIN_VALUE].
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public interface Integer
