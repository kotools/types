package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal object EmailAddressCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun patternSample() {
        val pattern: String = EmailAddress.PATTERN
        println(pattern) // ^\S+@\S+\.\S+$
    }
}
