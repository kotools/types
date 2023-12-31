/*
 * Copyright 2023 Loïc Lamarque and Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotools.types.internal.DeprecatedSince
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.Since
import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.TYPEALIAS

private const val OPT_IN_MESSAGE: String = "This declaration is experimental" +
        " and can be incompatibly changed in the future."

/** Marks declarations that are still **experimental** in the collection API. */
@Deprecated(
    "Use the ExperimentalKotoolsTypesApi annotation instead.",
    ReplaceWith(
        "ExperimentalKotoolsTypesApi",
        "kotools.types.experimental.ExperimentalKotoolsTypesApi"
    )
)
@DeprecatedSince(KotoolsTypesVersion.Unreleased)
@MustBeDocumented
@RequiresOptIn(OPT_IN_MESSAGE)
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalCollectionApi

/** Marks declarations that are still **experimental** in the API. */
@MustBeDocumented
@RequiresOptIn(OPT_IN_MESSAGE)
@Retention(BINARY)
@Since(KotoolsTypesVersion.Unreleased)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalKotoolsTypesApi

/** Marks declarations that are still **experimental** in the number API. */
@Deprecated(
    "Use the ExperimentalKotoolsTypesApi annotation instead.",
    ReplaceWith(
        "ExperimentalKotoolsTypesApi",
        "kotools.types.experimental.ExperimentalKotoolsTypesApi"
    )
)
@DeprecatedSince(KotoolsTypesVersion.Unreleased)
@MustBeDocumented
@RequiresOptIn(OPT_IN_MESSAGE)
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalNumberApi

/** Marks declarations that are still **experimental** in the range API. */
@Deprecated(
    "Use the ExperimentalKotoolsTypesApi annotation instead.",
    ReplaceWith(
        "ExperimentalKotoolsTypesApi",
        "kotools.types.experimental.ExperimentalKotoolsTypesApi"
    )
)
@DeprecatedSince(KotoolsTypesVersion.Unreleased)
@MustBeDocumented
@RequiresOptIn(OPT_IN_MESSAGE)
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalRangeApi

/** Marks declarations that are still **experimental** in the result API. */
@Deprecated(
    "Use the ExperimentalKotoolsTypesApi annotation instead.",
    ReplaceWith(
        "ExperimentalKotoolsTypesApi",
        "kotools.types.experimental.ExperimentalKotoolsTypesApi"
    )
)
@DeprecatedSince(KotoolsTypesVersion.Unreleased)
@MustBeDocumented
@RequiresOptIn(OPT_IN_MESSAGE)
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalResultApi

/** Marks declarations that are still **experimental** in the text API. */
@MustBeDocumented
@RequiresOptIn(OPT_IN_MESSAGE)
@Retention(BINARY)
@Since(KotoolsTypesVersion.V4_2_0)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalTextApi
