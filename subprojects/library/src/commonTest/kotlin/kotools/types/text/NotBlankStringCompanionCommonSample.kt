package kotools.types.text

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertNotNull

class NotBlankStringCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull() {
        val actual: NotBlankString? =
            NotBlankString.createOrNull("Kotools Types")
        assertNotNull(actual)
    }
}
