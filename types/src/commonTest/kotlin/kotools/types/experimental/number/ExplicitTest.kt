package kotools.types.experimental.number

import kotools.assert.assertEquals
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.random.Random
import kotlin.test.Test

@ExperimentalKotoolsTypesApi
class ExplicitXTest {
    @Test
    fun int_compareTo_should_pass_with_an_ExplicitNumberOfInt() {
        val x: Int = Random.nextInt()
        val y: NonZeroInt = NonZeroInt.random()
        val result: Int = x.compareTo(y)
        result assertEquals x.compareTo(y.value)
    }

    @Test
    fun explicitNumberOfInt_compareTo_should_pass_with_an_Int() {
        val x: NonZeroInt = NonZeroInt.random()
        val y: Int = Random.nextInt()
        val result: Int = x.compareTo(y)
        result assertEquals x.value.compareTo(y)
    }
}
