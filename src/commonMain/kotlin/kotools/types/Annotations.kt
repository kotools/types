package kotools.types

import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.*

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
