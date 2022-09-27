package kotools.types.collections

import kotools.assert.assertEquals
import kotools.assert.assertNotEquals
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.types.int.*
import kotlin.random.Random
import kotlin.test.Test

class NotEmptyCollectionTest {
    // ---------- Positional access operations ----------

    @Test
    fun getOrNull_should_pass_with_a_valid_index_as_an_Int() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        val index: Int = Random.nextInt(from = 0, until = collection.size)
        val result: Int? = collection getOrNull index
        result.assertNotNull() assertEquals collection.elementAt(index)
    }

    @Test
    fun getOrNull_should_return_null_with_an_index_as_an_Int_that_is_out_of_bounds() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        val result: Int? = collection getOrNull collection.size
        result.assertNull()
    }

    @Test
    fun getOrElse_should_pass_with_a_valid_index_as_an_Int() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        val index: Int = Random.nextInt(from = 0, until = collection.size)
        val defaultValue: Int = StrictlyNegativeInt.random.value
        val result: Int = collection.getOrElse(index) { defaultValue }
        result assertEquals collection.elementAt(index)
        result assertNotEquals defaultValue
    }

    @Test
    fun getOrElse_should_return_the_default_value_with_an_index_as_an_Int_that_is_out_of_bounds() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        val index: Int = collection.size
        val defaultValue: Int = StrictlyNegativeInt.random.value
        val result: Int = collection.getOrElse(index) { defaultValue }
        result assertEquals defaultValue
    }

    @Test
    fun getOrElse_should_pass_with_a_valid_index_as_a_PositiveInt() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        val index: PositiveInt =
            Random.nextInt(from = 0, until = collection.size).toPositiveInt()
        val defaultValue: Int = StrictlyNegativeInt.random.value
        val result: Int = collection.getOrElse(index) { defaultValue }
        result assertEquals collection.elementAt(index.value)
        result assertNotEquals defaultValue
    }

    @Test
    fun getOrElse_should_return_the_default_value_with_an_index_as_a_PositiveInt_that_is_out_of_bounds() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        val index = PositiveInt(collection.size)
        val defaultValue: Int = StrictlyNegativeInt.random.value
        val result: Int = collection.getOrElse(index) { defaultValue }
        result assertEquals defaultValue
    }

    @Test
    fun getOrElse_should_pass_with_a_valid_index_as_a_StrictlyPositiveInt() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        val index: StrictlyPositiveInt = Random.nextInt(
            from = 1,
            until = collection.size
        ).toStrictlyPositiveInt()
        val defaultValue: Int = StrictlyNegativeInt.random.value
        val result: Int = collection.getOrElse(index) { defaultValue }
        result assertEquals collection.elementAt(index.value)
        result assertNotEquals defaultValue
    }

    @Test
    fun getOrElse_should_return_the_default_value_with_an_index_as_a_StrictlyPositiveInt_that_is_out_of_bounds() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        val index = StrictlyPositiveInt(collection.size)
        val defaultValue: Int = StrictlyNegativeInt.random.value
        val result: Int = collection.getOrElse(index) { defaultValue }
        result assertEquals defaultValue
    }
}
