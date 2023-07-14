package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.contentShouldEqual
import kotools.types.shouldBeNotNull
import kotools.types.shouldEqual
import kotools.types.shouldHaveAMessage
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

class NotEmptyListTest {
    @Test
    fun notEmptyListOf_should_pass() {
        val head: Int = Random.nextInt()
        val tail: Array<Int> = List(2) { Random.nextInt() }
            .toTypedArray()
        val result: NotEmptyList<Int> = notEmptyListOf(head, *tail)
        result.toList() contentShouldEqual listOf(head) + tail
    }

    @Test
    fun collection_toNotEmptyList_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = List(3) { Random.nextInt() }
        val result: Result<NotEmptyList<Int>> = collection.toNotEmptyList()
        result.getOrThrow().toList() contentShouldEqual collection
    }

    @Test
    fun collection_toNotEmptyList_should_fail_with_an_empty_Collection() {
        val result: Result<NotEmptyList<Int>> = emptyList<Int>()
            .toNotEmptyList()
        val exception: IllegalArgumentException =
            assertFailsWith(block = result::getOrThrow)
        exception.shouldHaveAMessage()
    }

    @Test
    fun head_should_return_the_first_element_of_this_list() {
        val elements: NotEmptyList<Int> = List(3) { Random.nextInt() }
            .toNotEmptyList()
            .getOrThrow()
        val result: Int = elements.head
        result shouldEqual elements.toList().first()
    }

    @Test
    fun tail_should_return_all_elements_except_the_first_one() {
        val elements: NotEmptyList<Int> = List(3) { Random.nextInt() }
            .toNotEmptyList()
            .getOrThrow()
        val result: NotEmptyList<Int>? = elements.tail
        result.shouldBeNotNull()
            .toList() contentShouldEqual elements.toList().drop(1)
    }

    @Test
    fun tail_should_return_null_with_a_singleton_list() {
        val result: NotEmptyList<Int>? = notEmptyListOf(Random.nextInt()).tail
        assertNull(result)
    }

    @Test
    fun equals_should_pass_with_the_same_NotEmptyList() {
        // GIVEN
        val x: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        val y: NotEmptyList<Int> = x
        // WHEN & THEN
        assertEquals(x, y)
    }

    @Test
    fun equals_should_pass_with_another_NotEmptyList_having_the_same_values() {
        // GIVEN
        val x: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        val y: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        // WHEN & THEN
        assertEquals(x, y)
    }

    @Test
    fun equals_should_fail_with_another_NotEmptyList_having_another_head() {
        // GIVEN
        val x: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        val y: NotEmptyList<Int> = notEmptyListOf(-1, 2, 3)
        // WHEN & THEN
        assertNotEquals(x, y)
    }

    @Test
    fun equals_should_fail_with_another_NotEmptyList_having_another_tail() {
        // GIVEN
        val x: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        val y: NotEmptyList<Int> = notEmptyListOf(1, -2, -3)
        // WHEN & THEN
        assertNotEquals(x, y)
    }

    @Test
    fun toList_should_return_all_elements_as_a_List() {
        val elements: List<Int> = List(3) { Random.nextInt() }
        val result: List<Int> = elements.toNotEmptyList()
            .getOrThrow()
            .toList()
        result contentShouldEqual elements
    }

    @Test
    fun toString_should_behave_like_a_List() {
        val elements: NotEmptyList<Int> = List(3) { Random.nextInt() }
            .toNotEmptyList()
            .getOrThrow()
        "$elements" shouldEqual "${elements.toList()}"
    }
}

class NotEmptyListSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_NotEmptyList_as_serial_name() {
        // GIVEN
        val elementSerializer: KSerializer<Int> = Int.serializer()
        val serializer: KSerializer<NotEmptyList<Int>> =
            NotEmptyList.serializer(elementSerializer)
        // WHEN
        val actual: String = serializer.descriptor.serialName
        // THEN
        val expected: String = ListSerializer(elementSerializer)
            .descriptor
            .serialName
        assertEquals(expected, actual)
    }

    @Test
    fun serialization_should_behave_like_a_List() {
        val elements: NotEmptyList<Int> = List(3) { Random.nextInt() }
            .toNotEmptyList()
            .getOrThrow()
        val result: String = Json.encodeToString(elements)
        result shouldEqual Json.encodeToString(elements.toList())
    }

    @Test
    fun deserialization_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = List(3) { Random.nextInt() }
        val encoded: String = Json.encodeToString(collection)
        val result: NotEmptyList<Int> = Json.decodeFromString(encoded)
        result.toList() contentShouldEqual collection
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
