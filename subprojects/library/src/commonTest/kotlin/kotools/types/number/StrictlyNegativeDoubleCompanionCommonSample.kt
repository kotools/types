package kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class StrictlyNegativeDoubleCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun create() {
        val number: Number = -23
        val isSuccess: Boolean = try {
            StrictlyNegativeDouble.create(number)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull() {
        val number: Number = Random.nextInt(Int.MIN_VALUE until 0)
        val actual: StrictlyNegativeDouble? =
            StrictlyNegativeDouble.createOrNull(number)
        assertNotNull(actual)
    }
}
