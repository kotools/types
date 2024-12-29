package kotools.types.experimental

import kotools.types.number.AnyInt
import kotools.types.number.ZeroInt
import kotools.types.number.toStrictlyNegativeInt
import kotools.types.number.toStrictlyPositiveInt
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class AnyIntFactoryTest {
    @ExperimentalKotoolsTypesApi
    @Test
    fun constructor_like_function_should_pass_with_Int() {
        val value: Int = Random.nextInt()
        val actual = AnyInt(value)
        val expected: AnyInt = when {
            value > 0 -> value.toStrictlyPositiveInt().getOrThrow()
            value < 0 -> value.toStrictlyNegativeInt().getOrThrow()
            else -> ZeroInt
        }
        assertEquals(expected, actual)
    }
}
