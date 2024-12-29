package kotools.types.text

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class NotBlankStringCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun create() {
        val isSuccess: Boolean = try {
            NotBlankString.create("Kotools Types")
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull() {
        val actual: NotBlankString? =
            NotBlankString.createOrNull("Kotools Types")
        assertNotNull(actual)
    }
}
