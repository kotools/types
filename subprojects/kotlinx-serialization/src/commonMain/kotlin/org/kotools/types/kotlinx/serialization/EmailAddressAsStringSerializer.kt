package org.kotools.types.kotlinx.serialization

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.simpleNameOf
import org.kotools.types.EmailAddress
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Warning
import kotlin.reflect.KClass

/**
 * Class responsible for serializing the [EmailAddress] type as [String].
 *
 * @constructor Creates an instance of [EmailAddressAsStringSerializer].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this constructor from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.EmailAddressAsStringSerializerCommonSample.primaryConstructor]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public class EmailAddressAsStringSerializer {
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
     * SAMPLE: [org.kotools.types.kotlinx.serialization.EmailAddressAsStringSerializerCommonSample.toStringOverride]
     * </details>
     */
    @Suppress(Warning.FINAL)
    final override fun toString(): String = simpleNameOf(this::class)
}
