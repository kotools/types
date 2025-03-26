package kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertNotNull

internal class StrictlyNegativeIntCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull() {
        val number: StrictlyNegativeInt? = StrictlyNegativeInt.createOrNull(-7)
        assertNotNull(number)
    }
}
