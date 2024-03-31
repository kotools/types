@file:Suppress("unused")

package org.kotools.types

@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
internal annotation class ExperimentalSince(val version: KotoolsTypesVersion)

@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
internal annotation class Since(val version: KotoolsTypesVersion)

@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
internal annotation class DeprecatedSince(
    val warningSince: KotoolsTypesVersion = KotoolsTypesVersion.Unreleased,
    val errorSince: KotoolsTypesVersion = KotoolsTypesVersion.Unreleased,
    val hiddenSince: KotoolsTypesVersion = KotoolsTypesVersion.Unreleased
)
