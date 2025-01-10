package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as a **deprecated** feature with a
 * [warning][DeprecationLevel.WARNING] level.
 */
@InternalKotoolsTypesApi
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
public annotation class DeprecatedAsWarningSince(
    /**
     * The first version of Kotools Types where the declaration was deprecated
     * with a warning level.
     */
    val version: KotoolsTypesVersion
)
