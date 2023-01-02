package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.assertHasAMessage
import kotools.types.contentShouldEqual
import kotools.types.number.StrictlyPositiveInt
import kotlin.random.Random
import kotlin.test.*

class NotEmptyListTest {
    @Test
    fun head_should_return_the_first_element_of_this_list() {
        val elements: List<Int> = List(3) { Random.nextInt() }
        val result: Int = elements.asNotEmptyList.getOrThrow().head
        assertEquals(actual = result, expected = elements.first())
    }

    @Test
    fun tail_should_return_all_elements_except_the_first_one() {
        val elements: List<Int> = List(3) { Random.nextInt() }
        val result: NotEmptyList<Int>? =
            elements.asNotEmptyList.getOrThrow().tail
        assertEquals(
            actual = assertNotNull(result).asList,
            expected = elements.drop(1)
        )
    }

    @Test
    fun tail_should_return_null_with_a_singleton_list() {
        val result: NotEmptyList<Int>? = notEmptyListOf(Random.nextInt()).tail
        assertNull(result)
    }

    @Test
    fun asList_should_return_all_elements_as_a_List() {
        val elements: List<Int> = List(3) { Random.nextInt() }
        val result: List<Int> = elements.asNotEmptyList.getOrThrow().asList
        result contentShouldEqual elements
    }

    @Test
    fun size_should_return_the_size_of_this_list_as_a_StrictlyPositiveInt() {
        val elements: List<Int> = List(3) { Random.nextInt() }
        val result: StrictlyPositiveInt =
            elements.asNotEmptyList.getOrThrow().size
        assertEquals(actual = result.asInt, expected = elements.size)
    }

    @Test
    fun toString_should_behave_like_a_Collection() {
        val collection: Collection<Int> = List(3) { Random.nextInt() }
        val result = collection.asNotEmptyList.getOrThrow()
            .toString()
        assertEquals("$collection", result)
    }

    @Test
    fun collection_asNotEmptyList_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = List(3) { Random.nextInt() }
        val result: NotEmptyList<Int> = collection.asNotEmptyList.getOrThrow()
        assertContentEquals(actual = result.asList, expected = collection)
    }

    @Test
    fun collection_asNotEmptyList_should_fail_with_an_empty_Collection() {
        val result: Result<NotEmptyList<Int>> = emptyList<Int>().asNotEmptyList
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .assertHasAMessage()
    }

    @Test
    fun notEmptyListOf_should_pass() {
        val head: Int = Random.nextInt()
        val tail: Array<Int> = List(2) { Random.nextInt() }
            .toTypedArray()
        val result: NotEmptyList<Int> = notEmptyListOf(head, *tail)
        val expected: List<Int> = listOf(head) + tail
        assertContentEquals(actual = result.asList, expected = expected)
    }
}

class NotEmptyListSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_NotEmptyList_as_serial_name() {
        val result: String = NotEmptyList.serializer(Int.serializer())
            .descriptor
            .serialName
        assertEquals("${Package.collection}.NotEmptyList", result)
    }

    @Test
    fun serialization_should_behave_like_a_List() {
        val list: List<Int> = List(3) { Random.nextInt() }
        val notEmptyList: NotEmptyList<Int> = list.asNotEmptyList.getOrThrow()
        val result: String = Json.encodeToString(notEmptyList)
        val expected: String = Json.encodeToString(list)
        assertEquals(expected, result)
    }

    @Test
    fun deserialization_should_pass_with_a_not_empty_Collection() {
        val list: List<Int> = List(3) { Random.nextInt() }
        val encoded: String = Json.encodeToString(list)
        val result: NotEmptyList<Int> = Json.decodeFromString(encoded)
        assertContentEquals(actual = result.asList, expected = list)
    }

    @Test
    fun deserialization_should_fail_with_an_empty_Collection() {
        val list: List<Int> = emptyList()
        val encoded: String = Json.encodeToString(list)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<NotEmptyList<Int>>(encoded)
        }
        exception.assertHasAMessage()
    }
}
