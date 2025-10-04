package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerCommonSample {
    // ------------------------------- Companion -------------------------------

    @Test
    fun of() {
        assertNotNull(PositiveInteger of "+123456789")
    }
}
