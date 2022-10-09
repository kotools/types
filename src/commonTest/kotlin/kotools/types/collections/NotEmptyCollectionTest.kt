package kotools.types.collections

import kotools.assert.*
import kotools.types.core.RandomValueHolder
import kotools.types.number.PositiveIntHolder
import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.toPositiveInt
import kotools.types.string.NotBlankString
import kotlin.random.Random
import kotlin.test.Test

class NotEmptyCollectionTest : RandomValueHolder {
    // ---------- Positional access operations ----------

    @Test
    fun get_should_pass_with_a_valid_index_as_a_PositiveIntHolder() {
        val collection: NotEmptyCollection<Int> =
            notEmptyListOf(randomInt, randomInt, randomInt)
        val index: PositiveIntHolder = Random.nextInt(
            from = 0,
            until = collection.size
        ).toPositiveInt()
        val result: Int = collection[index]
        result assertEquals collection.elementAt(index.value)
    }

    @Test
    fun get_should_throw_an_error_with_an_index_as_a_PositiveIntHolder_that_is_out_of_bounds() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(randomInt)
        val index: PositiveIntHolder = collection.typedSize
        assertFailsWith<IndexOutOfBoundsException> { collection[index] }
    }

    @Suppress("DEPRECATION")
    @Test
    fun getOrNull_should_pass_with_a_valid_index_as_an_Int() {
        val collection: NotEmptyCollection<Int> =
            notEmptyListOf(randomInt, randomInt, randomInt)
        val index: Int = Random.nextInt(from = 0, until = collection.size)
        val result: Int? = collection getOrNull index
        result.assertNotNull() assertEquals collection.elementAt(index)
    }

    @Suppress("DEPRECATION")
    @Test
    fun getOrNull_should_return_null_with_an_index_as_an_Int_that_is_out_of_bounds() {
        val collection: NotEmptyCollection<Int> =
            notEmptyListOf(randomInt, randomInt, randomInt)
        val result: Int? = collection getOrNull collection.size
        result.assertNull()
    }

    @Test
    fun getOrNull_should_pass_with_a_valid_index_as_a_PositiveIntHolder() {
        val collection: NotEmptyCollection<Int> =
            notEmptyListOf(randomInt, randomInt, randomInt)
        val index: PositiveIntHolder = Random.nextInt(
            from = 0,
            until = collection.size
        ).toPositiveInt()
        val result: Int? = collection getOrNull index
        result.assertNotNull() assertEquals collection.elementAt(index.value)
    }

    @Test
    fun getOrNull_should_return_null_with_an_index_as_a_PositiveIntHolder_that_is_out_of_bounds() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(randomInt)
        val index: PositiveIntHolder = collection.typedSize
        val result: Int? = collection getOrNull index
        result.assertNull()
    }

    @Suppress("DEPRECATION")
    @Test
    fun getOrElse_should_pass_with_a_valid_index_as_an_Int() {
        val collection: NotEmptyCollection<Int> =
            notEmptyListOf(randomInt, randomInt, randomInt)
        val index: Int = Random.nextInt(from = 0, until = collection.size)
        val defaultValue: Int = StrictlyNegativeInt.random.value
        val result: Int = collection.getOrElse(index) { defaultValue }
        result assertEquals collection.elementAt(index)
        result assertNotEquals defaultValue
    }

    @Suppress("DEPRECATION")
    @Test
    fun getOrElse_should_return_the_default_value_with_an_index_as_an_Int_that_is_out_of_bounds() {
        val collection: NotEmptyCollection<Int> =
            notEmptyListOf(randomInt, randomInt, randomInt)
        val index: Int = collection.size
        val defaultValue: Int = StrictlyNegativeInt.random.value
        val result: Int = collection.getOrElse(index) { defaultValue }
        result assertEquals defaultValue
    }

    @Test
    fun getOrElse_should_pass_with_a_valid_index_as_a_PositiveIntHolder() {
        val collection: NotEmptyCollection<Int> =
            notEmptyListOf(randomInt, randomInt, randomInt)
        val index: PositiveIntHolder = Random.nextInt(
            from = 0,
            until = collection.size
        ).toPositiveInt()
        val defaultValue: Int = StrictlyNegativeInt.random.value
        val result: Int = collection.getOrElse(index) { defaultValue }
        result assertEquals collection.elementAt(index.value)
        result assertNotEquals defaultValue
    }

    @Test
    fun getOrElse_should_return_the_default_value_with_an_index_as_a_PositiveIntHolder_that_is_out_of_bounds() {
        val collection: NotEmptyCollection<Int> =
            notEmptyListOf(randomInt, randomInt, randomInt)
        val index: PositiveIntHolder = collection.typedSize
        val defaultValue: Int = StrictlyNegativeInt.random.value
        val result: Int = collection.getOrElse(index) { defaultValue }
        result assertEquals defaultValue
    }

    // ---------- Conversions ----------

    @Test
    fun toNotBlankString_should_pass() {
        val expectedList: List<Int> = listOf(randomInt, randomInt, randomInt)
        val collection: NotEmptyCollection<Int> = expectedList.toNotEmptyList()
        val result: NotBlankString = collection.toNotBlankString()
        result.value assertEquals expectedList.toString()
    }
}
