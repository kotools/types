package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmSynthetic
import kotlin.reflect.KClass

/**
 * Represents a class identity of type [T].
 *
 * You can use the [ClassIdentity.Companion.from] function for creating an
 * instance of this type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public class ClassIdentity<T : Any> private constructor() {
    /** Contains static declarations for the [ClassIdentity] type. */
    public companion object {
        /**
         * Creates an instance of [ClassIdentity] from the specified [type].
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: ClassIdentityCompanionKotlinSample.from.md
         *
         * The [KClass] type being unavailable for Java, this function is also
         * unavailable for this programming language.
         */
        @JvmSynthetic
        public fun <T : Any> from(
            @Suppress("UNUSED_PARAMETER") type: KClass<T>
        ): ClassIdentity<T> = ClassIdentity()
    }
}
