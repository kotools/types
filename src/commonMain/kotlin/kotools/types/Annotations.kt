package kotools.types

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared with the option [stability] level.
 *
 * The [version] should be in the following formats: `<major>.<minor>` or
 * `<major>.<minor>.<patch>`, where _major_, _minor_ and _patch_ are
 * non-negative integers without leading zeros.
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
internal annotation class SinceKotoolsTypes(
    val version: String,
    val stability: StabilityLevel = StabilityLevel.Stable
)

@Suppress("unused")
internal enum class StabilityLevel {
    /**
     * A component marked with the experimental level should be used only in toy
     * projects.
     * If the idea introduced by this component doesn't work out, we may drop it
     * any minute.
     */
    Experimental,

    /**
     * A component marked with the alpha level should be used wisely: migration
     * issues is still possible at this stage.
     */
    Alpha,

    /**
     * A component marked with the beta level can be used as a pre-stable stage.
     * At this stage, we should do our best to minimize migration issues for
     * users.
     */
    Beta,

    /**
     * A component marked with the stable level is considered as done and can be
     * used in production code.
     */
    Stable
}

/**
 * A component marked with this API level should be used only in toy projects.
 * If the idea introduced by this component doesn't work out, we may drop it
 * any minute.
 */
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn
@SinceKotoolsTypes("3.0", StabilityLevel.Alpha)
@Suppress("OPT_IN_IS_NOT_ENABLED")
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
public annotation class ExperimentalKotoolsTypesApi
