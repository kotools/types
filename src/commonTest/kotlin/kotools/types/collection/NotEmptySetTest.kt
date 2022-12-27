package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.assertHasAMessage
import kotools.types.number.StrictlyPositiveInt
import kotlin.random.Random
import kotlin.test.*

class NotEmptySetTest {
    @Test
    fun head_should_return_the_first_element_of_this_set() {
        val elements: Set<Int> = List(3) { Random.nextInt() }
            .toSet()
        val result: Int = elements.asNotEmptySet.getOrThrow().head
        assertEquals(actual = result, expected = elements.first())
    }

    @Test
    fun tail_should_return_all_elements_of_this_set_except_the_first_one() {
        val elements: Set<Int> = List(3) { Random.nextInt() }
            .toSet()
        val result: NotEmptySet<Int>? = elements.asNotEmptySet.getOrThrow().tail
        assertEquals(
            actual = assertNotNull(result).asSet,
            expected = elements.drop(1).toSet()
        )
    }

    @Test
    fun tail_should_return_null_with_a_singleton_set() {
        val result: NotEmptySet<Int>? = notEmptySetOf(Random.nextInt()).tail
        assertNull(result)
    }

    @Test
    fun size_should_return_the_size_of_this_set_as_a_StrictlyPositiveInt() {
        val elements: Set<Int> = List(3) { Random.nextInt() }
            .toSet()
        val result: StrictlyPositiveInt =
            elements.asNotEmptySet.getOrThrow().size
        assertEquals(actual = result.value, expected = elements.size)
    }

    @Test
    fun toString_should_behave_like_a_Set() {
        val elements: Set<Int> = List(8) { Random.nextInt() }
            .toSet()
        val result: String = elements.asNotEmptySet.getOrThrow()
            .toString()
        assertEquals("$elements", result)
    }

    @Test
    fun notEmptySetOf_should_pass() {
        val head: Int = Random.nextInt()
        val tail: Array<Int> = List(7) { Random.nextInt() }
            .toTypedArray()
        val result: NotEmptySet<Int> = notEmptySetOf(head, *tail)
        assertContentEquals(
            actual = result.asSet,
            expected = listOf(head) + tail
        )
    }

    @Test
    fun collection_toNotEmptySet_should_pass_with_a_not_empty_Collection() {
        val elements: List<Int> = List(8) { Random.nextInt() }
        val result: NotEmptySet<Int> = elements.asNotEmptySet.getOrThrow()
        assertContentEquals(actual = result.asSet, expected = elements)
    }

    @Test
    fun collection_toNotEmptySet_should_fail_with_an_empty_Collection() {
        val result: Result<NotEmptySet<Int>> = emptySet<Int>().asNotEmptySet
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .assertHasAMessage()
    }
}

class NotEmptySetSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_NotEmptySet_as_serial_name() {
        val result: String = NotEmptySet.serializer(Int.serializer())
            .descriptor
            .serialName
        assertEquals("${Package.collection}.NotEmptySet", result)
    }

    @Test
    fun serialization_should_behave_like_a_Set() {
        val elements: Set<Int> = List(8) { Random.nextInt() }
            .toSet()
        val notEmptyList: NotEmptySet<Int> = elements.asNotEmptySet.getOrThrow()
        val result: String = Json.encodeToString(notEmptyList)
        val expected: String = Json.encodeToString(elements)
        assertEquals(expected, result)
    }

    @Test
    fun deserialization_should_pass_with_a_not_empty_Collection() {
        val elements: Collection<Int> = List(8) { Random.nextInt() }
        val encoded: String = Json.encodeToString(elements)
        val result: NotEmptySet<Int> = Json.decodeFromString(encoded)
        assertContentEquals(actual = result.asSet, expected = elements)
    }

    @Test
    fun deserialization_should_fail_with_an_empty_Collection() {
        val collection: Collection<Int> = emptyList()
        val encoded: String = Json.encodeToString(collection)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<NotEmptySet<Int>>(encoded)
        }
        exception.assertHasAMessage()
    }
}
