package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class StrictlyNegativeIntCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun create() {
        val isSuccess: Boolean = try {
            StrictlyNegativeInt.create(-7)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull() {
        val number: StrictlyNegativeInt? = StrictlyNegativeInt.createOrNull(-7)
        assertNotNull(number)
    }
}
