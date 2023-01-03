package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.*
import kotools.types.number.StrictlyPositiveInt
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class NotEmptyListTest {
    @Test
    fun head_should_return_the_first_element_of_this_list() {
        val elements: NotEmptyList<Int> = List(3) { Random.nextInt() }
            .asNotEmptyList
            .getOrThrow()
        val result: Int = elements.head
        result shouldEqual elements.asList.first()
    }

    @Test
    fun tail_should_return_all_elements_except_the_first_one() {
        val elements: NotEmptyList<Int> = List(3) { Random.nextInt() }
            .asNotEmptyList
            .getOrThrow()
        val result: NotEmptyList<Int>? = elements.tail
        result.shouldBeNotNull()
            .asList contentShouldEqual elements.asList.drop(1)
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
        val elements: NotEmptyList<Int> = List(3) { Random.nextInt() }
            .asNotEmptyList
            .getOrThrow()
        val result: StrictlyPositiveInt = elements.size
        result.asInt shouldEqual elements.asList.size
    }

    @Test
    fun toString_should_behave_like_a_List() {
        val elements: NotEmptyList<Int> = List(3) { Random.nextInt() }
            .asNotEmptyList
            .getOrThrow()
        "$elements" shouldEqual "${elements.asList}"
    }

    @Test
    fun collection_asNotEmptyList_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = List(3) { Random.nextInt() }
        val result: Result<NotEmptyList<Int>> = collection.asNotEmptyList
        result.getOrThrow().asList contentShouldEqual collection
    }

    @Test
    fun collection_asNotEmptyList_should_fail_with_an_empty_Collection() {
        val result: Result<NotEmptyList<Int>> = emptyList<Int>().asNotEmptyList
        val exception: IllegalArgumentException =
            assertFailsWith(block = result::getOrThrow)
        exception.shouldHaveAMessage()
    }

    @Test
    fun notEmptyListOf_should_pass() {
        val head: Int = Random.nextInt()
        val tail: Array<Int> = List(2) { Random.nextInt() }
            .toTypedArray()
        val result: NotEmptyList<Int> = notEmptyListOf(head, *tail)
        result.asList contentShouldEqual listOf(head) + tail
    }
}

class NotEmptyListSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_NotEmptyList_as_serial_name() {
        val result: String = NotEmptyList.serializer(Int.serializer())
            .descriptor
            .serialName
        result shouldEqual "${Package.collection}.NotEmptyList"
    }

    @Test
    fun serialization_should_behave_like_a_List() {
        val elements: NotEmptyList<Int> = List(3) { Random.nextInt() }
            .asNotEmptyList
            .getOrThrow()
        val result: String = Json.encodeToString(elements)
        result shouldEqual Json.encodeToString(elements.asList)
    }

    @Test
    fun deserialization_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = List(3) { Random.nextInt() }
        val encoded: String = Json.encodeToString(collection)
        val result: NotEmptyList<Int> = Json.decodeFromString(encoded)
        result.asList contentShouldEqual collection
    }

    @Test
    fun deserialization_should_fail_with_an_empty_Collection() {
        val collection: Collection<Int> = emptyList()
        val encoded: String = Json.encodeToString(collection)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<NotEmptyList<Int>>(encoded)
        }
        exception.shouldHaveAMessage()
    }
}
