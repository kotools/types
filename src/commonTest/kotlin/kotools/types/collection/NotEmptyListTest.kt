package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.assertHasAMessage
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NotEmptyListTest {
    @Test
    fun toString_should_behave_like_a_Collection() {
        val collection: Collection<Int> = List(3) { Random.nextInt() }
        val result = collection.toNotEmptyList()
            .getOrThrow()
            .toString()
        assertEquals("$collection", result)
    }

    @Test
    fun notEmptyListOf_should_pass() {
        val head: Int = Random.nextInt()
        val tail: Array<Int> = List(2) { Random.nextInt() }
            .toTypedArray()
        val result: NotEmptyList<Int> = notEmptyListOf(head, *tail)
        val expected: List<Int> = listOf(head) + tail
        assertContentEquals(expected, result)
    }

    @Test
    fun collection_toNotEmptyList_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = List(3) { Random.nextInt() }
        val result: NotEmptyList<Int> = collection.toNotEmptyList()
            .getOrThrow()
        assertContentEquals(collection, result)
    }

    @Test
    fun collection_toNotEmptyList_should_fail_with_an_empty_Collection() {
        val result: Result<NotEmptyList<Int>> = emptyList<Int>()
            .toNotEmptyList()
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .assertHasAMessage()
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
        val notEmptyList: NotEmptyList<Int> = list.toNotEmptyList()
            .getOrThrow()
        val result: String = Json.encodeToString(notEmptyList)
        val expected: String = Json.encodeToString(list)
        assertEquals(expected, result)
    }

    @Test
    fun deserialization_should_pass_with_a_not_empty_Collection() {
        val list: List<Int> = List(3) { Random.nextInt() }
        val encoded: String = Json.encodeToString(list)
        val result: NotEmptyList<Int> = Json.decodeFromString(encoded)
        assertContentEquals(list, result)
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
