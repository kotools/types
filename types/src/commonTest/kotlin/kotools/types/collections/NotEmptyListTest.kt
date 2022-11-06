package kotools.types.collections

import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*
import kotools.types.*
import kotools.types.core.RandomValueHolder
import kotools.types.number.PositiveInt
import kotools.types.number.toPositiveIntOrThrow
import kotlin.test.Test

class NotEmptyListTest : RandomValueHolder {
    // ---------- Builders ----------
    @Test
    fun notEmptyListOf_should_pass(): Unit = randomInt
        .pairWith { arrayOf(randomInt) }
        .runPairWith { notEmptyListOf(first, *second) }
        .runMapFirst { listOf(first) + second }
        .toPairs()
        .forEach(Pair<Int?, Int?>::assertEquals)

    @Test
    fun collection_toNotEmptyList_should_pass_with_a_not_empty_Collection() =
        setOf(randomInt, randomInt, randomInt)
            .pairWith(Collection<Int>::toNotEmptyList)
            .toPairs()
            .forEach(Pair<Int?, Int?>::assertEquals)

    @Test
    fun collection_toNotEmptyList_should_throw_an_error_with_an_empty_Collection() {
        val collection: Collection<Int> = emptyList()
        assertFailsWith<IllegalArgumentException>(collection::toNotEmptyList)
    }

    @Test
    fun collection_toNotEmptyListOrNull_should_pass_with_a_not_empty_Collection() =
        setOf(randomString, randomString, randomString)
            .pairWith(Collection<String>::toNotEmptyListOrNull)
            .mapSecond(NotEmptyList<String>?::assertNotNull)
            .toPairs()
            .forEach(Pair<String?, String?>::assertEquals)

    @Test
    fun collection_toNotEmptyListOrNull_should_return_null_with_an_empty_Collection() =
        emptyList<String>()
            .toNotEmptyListOrNull()
            .assertNull()

    @Test
    fun collection_toNotEmptyListOrElse_should_pass_with_a_not_empty_Collection() =
        setOf(randomInt, randomInt, randomInt)
            .runPairWith { map(Int::unaryMinus) }
            .mapSecond(List<Int>::toNotEmptyList)
            .runPairWith {
                first toNotEmptyListOrElse { second }
            }
            .forEachSecondIndexed { index: PositiveInt,
                                    element: Int,
                                    (initialCollection: Collection<Int>,
                                        defaultValue: NotEmptyList<Int>) ->
                element assertEquals initialCollection.elementAt(index.value)
                element assertNotEquals defaultValue[index]
            }

    @Test
    fun collection_toNotEmptyListOrElse_should_return_the_default_value_with_an_empty_Collection() {
        val collection: Collection<Int> = emptyList()
        val defaultValue: NotEmptyList<Int> = notEmptyListOf(-1, -2, -3)
        val result: NotEmptyList<Int> =
            collection toNotEmptyListOrElse { defaultValue }
        result.forEachIndexed { index: Int, element: Int ->
            index.toPositiveIntOrThrow()
                .let(defaultValue::get)
                .assertEquals(element)
        }
    }

    @Test
    fun array_toNotEmptyListOrElse_should_pass_with_a_not_empty_Array() {
        val array: Array<Int> = arrayOf(1, 2, 3)
        val defaultValue: NotEmptyList<Int> = notEmptyListOf(-1, -2, -3)
        val result: NotEmptyList<Int> =
            array toNotEmptyListOrElse { defaultValue }
        result.forEachIndexed { index: Int, element: Int ->
            element assertEquals array.elementAt(index)
            index.toPositiveIntOrThrow()
                .let(defaultValue::get)
                .assertNotEquals(element)
        }
    }

    @Test
    fun array_toNotEmptyListOrElse_should_return_the_default_value_with_an_empty_Array() {
        val array: Array<Int> = emptyArray()
        val defaultValue: NotEmptyList<Int> = notEmptyListOf(-1, -2, -3)
        val result: NotEmptyList<Int> =
            array toNotEmptyListOrElse { defaultValue }
        result.forEachIndexed { index: Int, element: Int ->
            index.toPositiveIntOrThrow()
                .let(defaultValue::get)
                .assertEquals(element)
        }
    }

    // ---------- Conversions ----------

    @Test
    fun toString_should_behave_like_a_List() {
        val numbers: NotEmptyList<Int> = notEmptyListOf(randomInt, randomInt)
        val result: String = numbers.toString()
        result assertEquals numbers.toList().toString()
    }
}

class NotEmptyListSerializerTest : RandomValueHolder {
    @Test
    fun serialization_should_behave_like_a_List() {
        val serializer: NotEmptyListSerializer<Int> = Int.serializer()
            .let(::NotEmptyListSerializer)
        val numbers: NotEmptyList<Int> = notEmptyListOf(randomInt, randomInt)
        val result: String = Json.encodeToString(serializer, numbers)
        val list: List<Int> = numbers.toList()
        result assertEquals Json.encodeToString(list)
    }

    @Test
    fun deserialization_should_pass() {
        val list: List<Int> = notEmptyListOf(randomInt, randomInt)
        val encoded: String = Json.encodeToString(list)
        val serializer: NotEmptyListSerializer<Int> = Int.serializer()
            .let(::NotEmptyListSerializer)
        val result: NotEmptyList<Int> =
            Json.decodeFromString(serializer, encoded)
        result.size assertEquals list.size
        result.forEachIndexed { index: Int, number: Int ->
            number assertEquals list[index]
        }
    }
}
