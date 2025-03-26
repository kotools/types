package kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertNotNull

internal class StrictlyPositiveIntCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull() {
        val number: StrictlyPositiveInt? = StrictlyPositiveInt.createOrNull(42)
        assertNotNull(number)
    }
}
