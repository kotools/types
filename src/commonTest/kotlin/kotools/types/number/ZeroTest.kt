package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroTest {
    @Test
    fun equals_should_pass_with_the_same_instance_of_Zero() {
        val zero = Zero()
        val actual: Boolean = zero.equals(zero)
        assertTrue(actual, message = "Same instances of Zero should be equal.")
    }

    @Test
    fun equals_should_pass_with_another_instance_of_Zero() {
        val first = Zero()
        val second = Zero()
        val actual: Boolean = first.equals(second)
        assertTrue(actual, message = "Instances of Zero should be equal.")
    }

    @Test
    fun equals_should_fail_with_null() {
        val zero = Zero()
        val actual: Boolean = zero.equals(null)
        assertFalse(actual, message = "Zero shouldn't be equal to null.")
    }

    @Test
    fun equals_should_fail_with_an_object_having_another_type_than_Zero() {
        val first = Zero()
        val second = 0
        val actual: Boolean = first.equals(second)
        val message = "Zero shouldn't equal an object having another type."
        assertFalse(actual, message = message)
    }

    @Test
    fun hashCode_should_return_the_same_value_for_each_instances_of_Zero() {
        val actual: Int = Zero()
            .hashCode()
        val expected: Int = Zero()
            .hashCode()
        val message = "Instances of Zero should have the same hash code value."
        assertEquals(expected, actual, message)
    }

    @Test
    fun toString_should_return_the_string_representation_of_zero() {
        val actual: String = Zero()
            .toString()
        val expected = "0"
        val message = "String representation of Zero should be '0'."
        assertEquals(expected, actual, message)
    }
}
