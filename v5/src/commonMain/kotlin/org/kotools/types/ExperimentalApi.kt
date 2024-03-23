package org.kotools.types

import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Since

/**
 * Marks declarations that are still **experimental** in the
 * [Application Programming Interface (API)](https://en.wikipedia.org/wiki/API)
 * of Kotools Types.
 *
 * By definition, an **experimental** declaration can be
 * **incompatibly changed** in the future.
 * Thus, consumers should use these **experimental** declarations with caution.
 */
@MustBeDocumented
@RequiresOptIn("This declaration can be incompatibly changed in the future.")
@Retention(AnnotationRetention.BINARY)
@Since(KotoolsTypesVersion.Unreleased)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
public annotation class ExperimentalApi
