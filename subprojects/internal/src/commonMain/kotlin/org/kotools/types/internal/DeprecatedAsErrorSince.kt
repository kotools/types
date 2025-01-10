package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as a **deprecated** feature with an [error][DeprecationLevel.ERROR]
 * level.
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
public annotation class DeprecatedAsErrorSince(
    /**
     * The first version of Kotools Types where the declaration was deprecated
     * with an error level.
     */
    val version: KotoolsTypesVersion
)
