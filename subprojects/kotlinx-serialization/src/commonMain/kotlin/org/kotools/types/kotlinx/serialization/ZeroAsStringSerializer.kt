package org.kotools.types.kotlinx.serialization

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion

/**
 * Class responsible for serializing the [Zero] type as [String].
 *
 * @constructor Creates an instance of [ZeroAsStringSerializer].
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this constructor from Kotlin code:
 *
 * SAMPLE: [org.kotools.types.kotlinx.serialization.ZeroAsStringSerializerCommonSample.primaryConstructor]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_0)
public class ZeroAsStringSerializer
