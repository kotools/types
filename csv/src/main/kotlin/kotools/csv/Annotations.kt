package kotools.csv

/**
 * Specifies the first [version] of Kotools CSV where a declaration has
 * appeared.
 *
 * The [version] should be in the following formats: `<major>.<minor>` or
 * `<major>.<minor>.<patch>`, where _major_, _minor_ and _patch_ are
 * non-negative integers without leading zeros.
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
internal annotation class SinceKotoolsCsv(val version: String)
