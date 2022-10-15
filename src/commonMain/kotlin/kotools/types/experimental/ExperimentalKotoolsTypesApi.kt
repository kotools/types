package kotools.types.experimental

import kotools.types.SinceKotoolsTypes
import kotools.types.StabilityLevel

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
