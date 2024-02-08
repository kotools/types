@file:OptIn(InternalKotoolsTypesApi::class)

package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.contentShouldEqual
import kotools.types.internal.ErrorMessage
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.shouldBeNotNull
import kotools.types.shouldBeNull
import kotools.types.shouldEqual
import kotools.types.shouldFailWithIllegalArgumentException
import kotools.types.shouldHaveAMessage
import kotools.types.shouldNotEqual
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NotEmptySetTest {
    @Test
    fun notEmptySetOf_should_pass() {
        val head: Int = Random.nextInt()
        val tail: Array<Int> = List(2) { Random.nextInt() }
            .toTypedArray()
        val result: NotEmptySet<Int> = notEmptySetOf(head, *tail)
        val expected: Set<Int> = setOf(head) + tail
        result.toSet() contentShouldEqual expected
    }

    @Test
    fun collection_toNotEmptySet_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = List(3) { Random.nextInt() }
        val result: Result<NotEmptySet<Int>> = collection.toNotEmptySet()
        result.getOrThrow()
            .toSet() contentShouldEqual collection
    }

    @Test
    fun collection_toNotEmptySet_should_fail_with_an_empty_Collection() {
        val collection: Collection<Int> = emptySet()
        val result: Result<NotEmptySet<Int>> = collection.toNotEmptySet()
        result.shouldFailWithIllegalArgumentException { getOrThrow() }
            .shouldHaveAMessage()
    }

    @Test
    fun head_should_return_the_first_element_of_this_set() {
        val set: NotEmptySet<Int> = List(3) { Random.nextInt() }
            .toNotEmptySet()
            .getOrThrow()
        val result: Int = set.head
        val expected: Int = set.toSet()
            .first()
        result shouldEqual expected
    }

    @Test
    fun tail_should_return_all_elements_of_this_set_except_the_first_one() {
        val set: NotEmptySet<Int> = List(3) { Random.nextInt() }
            .toNotEmptySet()
            .getOrThrow()
        val result: NotEmptySet<Int>? = set.tail
        val expected: List<Int> = set.toSet().drop(1)
        result.shouldBeNotNull()
            .toSet() contentShouldEqual expected
    }

    @Test
    fun tail_should_return_null_with_a_singleton_set() {
        val set: NotEmptySet<Int> = notEmptySetOf(Random.nextInt())
        val result: NotEmptySet<Int>? = set.tail
        result.shouldBeNull()
    }

    @Test
    fun equals_should_pass_with_the_same_NotEmptySet() {
        val x: NotEmptySet<Int> = notEmptySetOf(1, 2, 3)
        val y: NotEmptySet<Int> = x
        x shouldEqual y
    }

    @Test
    fun equals_should_pass_with_another_NotEmptySet_having_the_same_values() {
        val x: NotEmptySet<Int> = notEmptySetOf(1, 2, 3)
        val y: NotEmptySet<Int> = notEmptySetOf(1, 2, 3)
        x shouldEqual y
    }

    @Test
    fun equals_should_fail_with_another_NotEmptySet_having_another_head() {
        val x: NotEmptySet<Int> = notEmptySetOf(1, 2, 3)
        val y: NotEmptySet<Int> = notEmptySetOf(-1, 2, 3)
        x shouldNotEqual y
    }

    @Test
    fun equals_should_fail_with_another_NotEmptySet_having_another_tail() {
        val x: NotEmptySet<Int> = notEmptySetOf(1, 2, 3)
        val y: NotEmptySet<Int> = notEmptySetOf(1, -2, -3)
        x shouldNotEqual y
    }

    @Test
    fun toSet_should_return_all_elements_as_a_Set() {
        val expected: Set<Int> = List(3) { Random.nextInt() }
            .toSet()
        val set: NotEmptySet<Int> = expected.toNotEmptySet()
            .getOrThrow()
        val result: Set<Int> = set.toSet()
        result contentShouldEqual expected
    }

    @Test
    fun toString_should_behave_like_a_Set() {
        val elements: NotEmptySet<Int> = List(3) { Random.nextInt() }
            .toNotEmptySet()
            .getOrThrow()
        "$elements" shouldEqual "${elements.toSet()}"
    }
}

class NotEmptySetIntegrationTest {
    @Test
    fun updating_the_original_MutableCollection_should_not_impact_the_NotEmptySet() {
        val original: MutableCollection<Int> = mutableSetOf(1, 2, 3)
        val notEmptySet: NotEmptySet<Int> = original.toNotEmptySet()
            .getOrThrow()
        "$original" shouldEqual "$notEmptySet"
        original.clear()
        "$original" shouldNotEqual "$notEmptySet"
    }
}

class NotEmptySetSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_NotEmptySet_as_serial_name() {
        val elementSerializer: KSerializer<Int> = Int.serializer()
        val serializer: KSerializer<NotEmptySet<Int>> =
            NotEmptySet.serializer(elementSerializer)
        val result: String = serializer.descriptor.serialName
        val expected: String = SetSerializer(elementSerializer)
            .descriptor
            .serialName
        result shouldEqual expected
    }

    @Test
    fun serialization_should_behave_like_a_Set() {
        val elements: NotEmptySet<Int> = List(3) { Random.nextInt() }
            .toNotEmptySet()
            .getOrThrow()
        val result: String = Json.encodeToString(elements)
        val expected: String = Json.encodeToString(elements.toSet())
        result shouldEqual expected
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
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage = ErrorMessage.emptyCollection
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun serialization_processes_of_wrapped_NotEmptySet_should_pass() {
        @Serializable
        data class Wrapper(val values: NotEmptySet<Int>)

        val wrapper: Wrapper = List(3) { Random.nextInt() }
            .toNotEmptySet()
            .map { Wrapper(it) }
            .getOrThrow()
        val encoded: String = Json.encodeToString(wrapper)
        val decoded: Wrapper = Json.decodeFromString(encoded)
        assertEquals(wrapper.values, decoded.values)
    }
}
