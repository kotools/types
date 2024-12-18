package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class StrictlyPositiveIntCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun create() {
        val isSuccess: Boolean = try {
            StrictlyPositiveInt.create(42)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull() {
        val number: StrictlyPositiveInt? = StrictlyPositiveInt.createOrNull(42)
        assertNotNull(number)
    }
}
