package kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NonZeroIntCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull() {
        val number: NonZeroInt? = NonZeroInt.createOrNull(23)
        assertEquals(expected = "23", actual = "$number")
    }
}
