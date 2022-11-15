package kotools.types.collections

import kotools.assert.*
import kotools.types.core.RandomValueHolder
import kotools.types.number.PositiveInt
import kotools.types.number.randomStrictlyNegativeInt
import kotools.types.number.toPositiveIntOrThrow
import kotools.types.string.NotBlankString
import kotlin.random.Random
import kotlin.test.Test

class NotEmptyCollectionTest : RandomValueHolder {
    // ---------- Positional access operations ----------

    @Test
    fun get_should_pass_with_a_valid_index_as_a_PositiveInt() {
        val collection: NotEmptyCollection<Int> =
            notEmptyListOf(randomInt, randomInt, randomInt)
        val index: PositiveInt = Random.nextInt(
            from = 0,
            until = collection.size
        ).toPositiveIntOrThrow()
        val result: Int = collection[index]
        result assertEquals collection.elementAt(index.value)
    }

    @Test
    fun get_should_throw_an_error_with_an_index_as_a_PositiveInt_that_is_out_of_bounds() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(randomInt)
        val index: PositiveInt = collection.typedSize
        assertFailsWith<IndexOutOfBoundsException> { collection[index] }
    }

    @Test
    fun getOrNull_should_pass_with_a_valid_index_as_a_PositiveInt() {
        val collection: NotEmptyCollection<Int> =
            notEmptyListOf(randomInt, randomInt, randomInt)
        val index: PositiveInt = Random.nextInt(
            from = 0,
            until = collection.size
        ).toPositiveIntOrThrow()
        val result: Int? = collection getOrNull index
        result.assertNotNull() assertEquals collection.elementAt(index.value)
    }

    @Test
    fun getOrNull_should_return_null_with_an_index_as_a_PositiveInt_that_is_out_of_bounds() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(randomInt)
        val index: PositiveInt = collection.typedSize
        val result: Int? = collection getOrNull index
        result.assertNull()
    }

    @Test
    fun getOrElse_should_pass_with_a_valid_index_as_a_PositiveInt() {
        val collection: NotEmptyCollection<Int> =
            notEmptyListOf(randomInt, randomInt, randomInt)
        val index: PositiveInt = Random.nextInt(
            from = 0,
            until = collection.size
        ).toPositiveIntOrThrow()
        val defaultValue: Int = randomStrictlyNegativeInt().value
        val result: Int = collection.getOrElse(index) { defaultValue }
        result assertEquals collection.elementAt(index.value)
        result assertNotEquals defaultValue
    }

    @Test
    fun getOrElse_should_return_the_default_value_with_an_index_as_a_PositiveInt_that_is_out_of_bounds() {
        val collection: NotEmptyCollection<Int> =
            notEmptyListOf(randomInt, randomInt, randomInt)
        val index: PositiveInt = collection.typedSize
        val defaultValue: Int = randomStrictlyNegativeInt().value
        val result: Int = collection.getOrElse(index) { defaultValue }
        result assertEquals defaultValue
    }

    // ---------- Conversions ----------

    @Test
    fun toNotBlankString_should_pass() {
        val expectedList: List<Int> = listOf(randomInt, randomInt, randomInt)
        val collection: NotEmptyCollection<Int> =
            expectedList.toNotEmptyListOrThrow()
        val result: NotBlankString = collection.toNotBlankString()
        result.value assertEquals expectedList.toString()
    }
}
