package kotools.types

import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.TYPEALIAS

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as an **experimental** feature.
 *
 * The [version] should be in the following formats: `<major>.<minor>` or
 * `<major>.<minor>.<patch>`, where _major_, _minor_ and _patch_ are positive
 * integers without leading zeros.
 */
@MustBeDocumented
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
internal annotation class ExperimentalSinceKotoolsTypes(val version: String)

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as a **stable** feature.
 *
 * The [version] should be in the following formats: `<major>.<minor>` or
 * `<major>.<minor>.<patch>`, where _major_, _minor_ and _patch_ are positive
 * integers without leading zeros.
 */
@MustBeDocumented
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
internal annotation class SinceKotoolsTypes(val version: String)

/**
 * Specifies the first versions of Kotools Types where a declaration has
 * appeared as a **deprecated** feature.
 *
 * The specified [warningSince] should match the version where the declaration
 * was deprecated with a [warning][DeprecationLevel.WARNING] level.
 * The specified [errorSince] should match the version where it was deprecated
 * with an [error][DeprecationLevel.ERROR] level.
 * The specified [hiddenSince] should match the version where the declaration
 * was deprecated with a [hidden][DeprecationLevel.HIDDEN] level.
 *
 * The versions should be in the following formats: `<major>.<minor>` or
 * `<major>.<minor>.<patch>`, where _major_, _minor_ and _patch_ are positive
 * integers without leading zeros.
 */
@MustBeDocumented
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
internal annotation class DeprecatedSinceKotoolsTypes(
    val warningSince: String,
    val errorSince: String = "",
    val hiddenSince: String = ""
)
