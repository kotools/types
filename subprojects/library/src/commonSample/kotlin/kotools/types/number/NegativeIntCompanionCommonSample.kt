package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NegativeIntCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun create() {
        val number: NegativeInt = NegativeInt.create(-7)
        assertEquals(expected = "-7", actual = "$number")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull() {
        val number: NegativeInt? = NegativeInt.createOrNull(-7)
        assertEquals(expected = "-7", actual = "$number")
    }
}
