package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test

class ClassIdentityCompanionTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun from_should_pass() {
        ClassIdentity.from(Zero::class)
    }
}
