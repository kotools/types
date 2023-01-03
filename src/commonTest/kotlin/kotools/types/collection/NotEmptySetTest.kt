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

class NotEmptySetTest {
    @Test
    fun head_should_return_the_first_element_of_this_set() {
        val elements: NotEmptySet<Int> = List(3) { Random.nextInt() }
            .asNotEmptySet
            .getOrThrow()
        val result: Int = elements.head
        result shouldEqual elements.asSet.first()
    }

    @Test
    fun tail_should_return_all_elements_of_this_set_except_the_first_one() {
        val elements: NotEmptySet<Int> = List(3) { Random.nextInt() }
            .asNotEmptySet
            .getOrThrow()
        val result: NotEmptySet<Int>? = elements.tail
        result.shouldBeNotNull().asSet contentShouldEqual elements.asSet.drop(1)
    }

    @Test
    fun tail_should_return_null_with_a_singleton_set() {
        val result: NotEmptySet<Int>? = notEmptySetOf(Random.nextInt()).tail
        assertNull(result)
    }

    @Test
    fun asSet_should_return_all_elements_as_a_Set() {
        val elements: Set<Int> = List(3) { Random.nextInt() }
            .toSet()
        val result: Set<Int> = elements.asNotEmptySet.getOrThrow().asSet
        result contentShouldEqual elements
    }

    @Test
    fun size_should_return_the_size_of_this_set_as_a_StrictlyPositiveInt() {
        val elements: NotEmptySet<Int> = List(3) { Random.nextInt() }
            .asNotEmptySet
            .getOrThrow()
        val result: StrictlyPositiveInt = elements.size
        result.asInt shouldEqual elements.asSet.size
    }

    @Test
    fun toString_should_behave_like_a_Set() {
        val elements: NotEmptySet<Int> = List(3) { Random.nextInt() }
            .asNotEmptySet
            .getOrThrow()
        "$elements" shouldEqual "${elements.asSet}"
    }

    @Test
    fun collection_asNotEmptySet_should_pass_with_a_not_empty_Collection() {
        val elements: List<Int> = List(3) { Random.nextInt() }
        val result: Result<NotEmptySet<Int>> = elements.asNotEmptySet
        result.getOrThrow().asSet contentShouldEqual elements
    }

    @Test
    fun collection_asNotEmptySet_should_fail_with_an_empty_Collection() {
        val result: Result<NotEmptySet<Int>> = emptySet<Int>().asNotEmptySet
        val exception: IllegalArgumentException =
            assertFailsWith(block = result::getOrThrow)
        exception.shouldHaveAMessage()
    }

    @Test
    fun notEmptySetOf_should_pass() {
        val head: Int = Random.nextInt()
        val tail: Array<Int> = List(2) { Random.nextInt() }
            .toTypedArray()
        val result: NotEmptySet<Int> = notEmptySetOf(head, *tail)
        result.asSet contentShouldEqual listOf(head) + tail
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
            .asNotEmptySet
            .getOrThrow()
        val result: String = Json.encodeToString(elements)
        result shouldEqual Json.encodeToString(elements.asSet)
    }

    @Test
    fun deserialization_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = List(3) { Random.nextInt() }
        val encoded: String = Json.encodeToString(collection)
        val result: NotEmptySet<Int> = Json.decodeFromString(encoded)
        result.asSet contentShouldEqual collection
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
