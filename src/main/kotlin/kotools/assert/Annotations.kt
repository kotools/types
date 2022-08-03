package kotools.assert

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

/**
 * Specifies the first [version] of Kotools Assert where a declaration has
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
internal annotation class SinceKotoolsAssert(val version: String)

@SinceKotoolsAssert("2.1")
public typealias Nested = Nested

@SinceKotoolsAssert("2.1")
public typealias Test = Test
