package kotools.types.experimental

import kotools.shared.Project.Types
import kotools.shared.SinceKotools

/**
 * A component marked with this API level should be used only in toy projects.
 * If the idea introduced by this component doesn't work out, we may drop it
 * any minute.
 */
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn
@SinceKotools(Types, "3.0")
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
