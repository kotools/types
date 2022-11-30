package kotools.types.experimental.number

import kotools.assert.assertEquals
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.random.Random
import kotlin.test.Test

@ExperimentalKotoolsTypesApi
class ExplicitXTest {
    @Test
    fun int_compareTo_should_pass_with_an_ExplicitInt() {
        val x: Int = Random.nextInt()
        val y: NonZeroInt = NonZeroInt.random()
        val result: Int = x.compareTo(y)
        result assertEquals x.compareTo(y.value)
    }
}
