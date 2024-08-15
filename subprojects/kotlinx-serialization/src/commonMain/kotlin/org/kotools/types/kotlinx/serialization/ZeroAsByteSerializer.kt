package org.kotools.types.kotlinx.serialization

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import org.kotools.types.Zero
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import kotlin.reflect.KClass

/**
 * Class responsible for serializing the [Zero] type as [Byte].
 *
 * @constructor Creates an instance of [ZeroAsByteSerializer].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this constructor from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroAsByteSerializerCommonSample.primaryConstructor]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public class ZeroAsByteSerializer {
    /**
     * Returns `true` if the [other] object is an instance of
     * [ZeroAsByteSerializer], or returns `false` otherwise.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this method from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroAsByteSerializerCommonSample.equalsOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun equals(other: Any?): Boolean =
        other is ZeroAsByteSerializer

    /**
     * Returns a hash code value for this serializer.
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this method from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroAsByteSerializerCommonSample.hashCodeOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun hashCode(): Int = hashCodeOf("$this")

    /**
     * Returns the string representation of this serializer, corresponding to
     * its [simple name][KClass.simpleName].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this method from Kotlin code:
     *
     * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroAsByteSerializerCommonSample.toStringOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = simpleNameOf(this::class)
}
