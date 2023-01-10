package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.*
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class NotEmptySetTest {
    @Test
    fun head_should_return_the_first_element_of_this_set() {
        val elements: NotEmptySet<Int> = List(3) { Random.nextInt() }
            .toNotEmptySet()
            .getOrThrow()
        val result: Int = elements.head
        result shouldEqual elements.toSet().first()
    }

    @Test
    fun tail_should_return_all_elements_of_this_set_except_the_first_one() {
        val elements: NotEmptySet<Int> = List(3) { Random.nextInt() }
            .toNotEmptySet()
            .getOrThrow()
        val result: NotEmptySet<Int>? = elements.tail
        val expected: List<Int> = elements.toSet().drop(1)
        result.shouldBeNotNull().toSet() contentShouldEqual expected
    }

    @Test
    fun tail_should_return_null_with_a_singleton_set() {
        val result: NotEmptySet<Int>? = notEmptySetOf(Random.nextInt()).tail
        assertNull(result)
    }

    @Test
    fun toSet_should_return_all_elements_as_a_Set() {
        val elements: Set<Int> = List(3) { Random.nextInt() }
            .toSet()
        val result: Set<Int> = elements.toNotEmptySet().getOrThrow().toSet()
        result contentShouldEqual elements
    }

    @Test
    fun toString_should_behave_like_a_Set() {
        val elements: NotEmptySet<Int> = List(3) { Random.nextInt() }
            .toNotEmptySet()
            .getOrThrow()
        "$elements" shouldEqual "${elements.toSet()}"
    }

    @Test
    fun notEmptySetOf_should_pass() {
        val head: Int = Random.nextInt()
        val tail: Array<Int> = List(2) { Random.nextInt() }
            .toTypedArray()
        val result: NotEmptySet<Int> = notEmptySetOf(head, *tail)
        result.toSet() contentShouldEqual listOf(head) + tail
    }

    @Test
    fun collection_toNotEmptySet_should_pass_with_a_not_empty_Collection() {
        val elements: List<Int> = List(3) { Random.nextInt() }
        val result: Result<NotEmptySet<Int>> = elements.toNotEmptySet()
        result.getOrThrow().toSet() contentShouldEqual elements
    }

    @Test
    fun collection_toNotEmptySet_should_fail_with_an_empty_Collection() {
        val result: Result<NotEmptySet<Int>> = emptySet<Int>()
            .toNotEmptySet()
        val exception: IllegalArgumentException =
            assertFailsWith(block = result::getOrThrow)
        exception.shouldHaveAMessage()
    }
}

class NotEmptySetSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_NotEmptySet_as_serial_name() {
        val result: String = NotEmptySet.serializer(Int.serializer())
            .descriptor
            .serialName
        result shouldEqual "${Package.collection}.NotEmptySet"
    }

    @Test
    fun serialization_should_behave_like_a_Set() {
        val elements: NotEmptySet<Int> = List(3) { Random.nextInt() }
            .toNotEmptySet()
            .getOrThrow()
        val result: String = Json.encodeToString(elements)
        result shouldEqual Json.encodeToString(elements.toSet())
    }

    @Test
    fun deserialization_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = List(3) { Random.nextInt() }
        val encoded: String = Json.encodeToString(collection)
        val result: NotEmptySet<Int> = Json.decodeFromString(encoded)
        result.toSet() contentShouldEqual collection
    }

    @Test
    fun deserialization_should_fail_with_an_empty_Collection() {
        val collection: Collection<Int> = emptyList()
        val encoded: String = Json.encodeToString(collection)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<NotEmptySet<Int>>(encoded)
        }
        exception.shouldHaveAMessage()
    }
}
