package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NonZeroIntCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun create() {
        val number: NonZeroInt = NonZeroInt.create(23)
        assertEquals(expected = "23", actual = "$number")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull() {
        val number: NonZeroInt? = NonZeroInt.createOrNull(23)
        assertEquals(expected = "23", actual = "$number")
    }
}
