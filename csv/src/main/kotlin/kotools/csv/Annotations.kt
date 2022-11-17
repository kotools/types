package kotools.csv

import kotools.shared.StabilityLevel

/**
 * Specifies the first [version] of Kotools CSV where a declaration has
 * appeared with the optional [stability] level.
 *
 * The [version] should be in the following formats: `<major>.<minor>` or
 * `<major>.<minor>.<patch>`, where _major_, _minor_ and _patch_ are positive
 * integers without leading zeros.
 *
 * The component marked with this annotation is considered as
 * [StabilityLevel.Stable] if no [stability] was provided explicitly.
 */
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.TYPEALIAS
)
internal annotation class SinceKotoolsCsv(
    val version: String,
    val stability: StabilityLevel = StabilityLevel.Stable
)
