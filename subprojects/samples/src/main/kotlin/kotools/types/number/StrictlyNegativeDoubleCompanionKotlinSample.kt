package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.random.Random
import kotlin.random.nextInt

internal class StrictlyNegativeDoubleCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun create() {
        val number: Number = -23
        val isSuccess: Boolean = try {
            StrictlyNegativeDouble.create(number)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        println(isSuccess) // true
    } // END

    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun createOrNull() {
        val number: Number = Random.nextInt(Int.MIN_VALUE until 0)
        val result: StrictlyNegativeDouble? =
            StrictlyNegativeDouble.createOrNull(number)
        println(result != null) // true
    } // END
}
