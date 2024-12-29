package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.contentShouldEqual
import kotools.types.internal.ErrorMessage
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.number.StrictlyPositiveInt
import kotools.types.shouldBeNotNull
import kotools.types.shouldBeNull
import kotools.types.shouldEqual
import kotools.types.shouldFailWithIllegalArgumentException
import kotools.types.shouldHaveAMessage
import kotools.types.shouldNotEqual
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class NotEmptyListCompanionTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun create_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = listOf(1, 2, 3)
        val elements: NotEmptyList<Int> = NotEmptyList.create(collection)
        val actual: Collection<Int> = elements.toList()
        assertContentEquals(expected = collection, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun create_should_pass_with_a_not_empty_MutableCollection() {
        val original: MutableCollection<Int> = mutableListOf(1, 2, 3)
        val elements: NotEmptyList<Int> = NotEmptyList.create(original)
        assertEquals(expected = "$original", actual = "$elements")
        original.clear()
        assertNotEquals(illegal = "$original", actual = "$elements")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun create_should_fail_with_an_empty_Collection() {
        val collection: Collection<Int> = emptyList()
        val exception: IllegalArgumentException = assertFailsWith {
            NotEmptyList.create(collection)
        }
        val actual = ErrorMessage(exception)
        assertEquals(expected = ErrorMessage.emptyCollection, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = listOf(1, 2, 3)
        val elements: NotEmptyList<Int>? = NotEmptyList.createOrNull(collection)
        assertNotNull(elements, "$collection to NotEmptyList shouldn't fail")
        val actual: Collection<Int> = elements.toList()
        assertContentEquals(expected = collection, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_pass_with_a_not_empty_MutableCollection() {
        val original: MutableCollection<Int> = mutableListOf(1, 2, 3)
        val elements: NotEmptyList<Int>? = NotEmptyList.createOrNull(original)
        assertNotNull(elements, "$original to NotEmptyList shouldn't fail")
        assertEquals(expected = "$original", actual = "$elements")
        original.clear()
        assertNotEquals(illegal = "$original", actual = "$elements")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_fail_with_an_empty_Collection() {
        val collection: Collection<Int> = emptyList()
        val elements: NotEmptyList<Int>? = NotEmptyList.createOrNull(collection)
        assertNull(elements, "$collection to NotEmptyList should fail")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun of_should_pass_with_a_head() {
        val head = 1
        val integers: NotEmptyList<Int> = NotEmptyList.of(head)
        val expectedSize: StrictlyPositiveInt = StrictlyPositiveInt.create(1)
        assertEquals(expectedSize, actual = integers.size)
        assertEquals(expected = head, actual = integers.head)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun of_should_pass_with_a_head_and_a_tail() {
        val head = 1
        val tail: Array<Int> = arrayOf(2, 3)
        val integers: NotEmptyList<Int> = NotEmptyList.of(head, *tail)
        val expectedSize: StrictlyPositiveInt =
            StrictlyPositiveInt.create(1 + tail.size)
        assertEquals(expectedSize, actual = integers.size)
        assertEquals(expected = head, actual = integers.head)
        val actualTail: Array<Int> = assertNotNull(integers.tail)
            .toList()
            .toTypedArray()
        assertContentEquals(expected = tail, actualTail)
    }
}

class NotEmptyListTest {
    @Test
    fun notEmptyListOf_should_pass() {
        val head: Int = Random.nextInt()
        val tail: Array<Int> = List(2) { Random.nextInt() }
            .toTypedArray()
        val result: NotEmptyList<Int> = notEmptyListOf(head, *tail)
        val expected: List<Int> = listOf(head) + tail
        result.toList() contentShouldEqual expected
    }

    @Test
    fun collection_toNotEmptyList_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = List(3) { Random.nextInt() }
        val result: Result<NotEmptyList<Int>> = collection.toNotEmptyList()
        result.getOrThrow().toList() contentShouldEqual collection
    }

    @Test
    fun collection_toNotEmptyList_should_fail_with_an_empty_Collection() {
        val collection: Collection<Int> = emptyList()
        val result: Result<NotEmptyList<Int>> = collection.toNotEmptyList()
        result.shouldFailWithIllegalArgumentException { getOrThrow() }
            .shouldHaveAMessage()
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
        result.shouldBeNull()
    }

    @Test
    fun equals_should_pass_with_the_same_NotEmptyList() {
        val x: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        val y: NotEmptyList<Int> = x
        x shouldEqual y
    }

    @Test
    fun equals_should_pass_with_another_NotEmptyList_having_the_same_values() {
        val x: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        val y: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        x shouldEqual y
    }

    @Test
    fun equals_should_fail_with_another_NotEmptyList_having_another_head() {
        val x: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        val y: NotEmptyList<Int> = notEmptyListOf(-1, 2, 3)
        x shouldNotEqual y
    }

    @Test
    fun equals_should_fail_with_another_NotEmptyList_having_another_tail() {
        val x: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        val y: NotEmptyList<Int> = notEmptyListOf(1, -2, -3)
        x shouldNotEqual y
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

class NotEmptyListIntegrationTest {
    @Test
    fun updating_the_original_MutableCollection_should_not_impact_the_NotEmptyList() {
        val original: MutableCollection<Int> = mutableListOf(1, 2, 3)
        val notEmptyList: NotEmptyList<Int> = original.toNotEmptyList()
            .getOrThrow()
        "$original" shouldEqual "$notEmptyList"
        original.clear()
        "$original" shouldNotEqual "$notEmptyList"
    }
}

class NotEmptyListSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_NotEmptyList_as_serial_name() {
        val elementSerializer: KSerializer<Int> = Int.serializer()
        val serializer: KSerializer<NotEmptyList<Int>> =
            NotEmptyList.serializer(elementSerializer)
        val result: String = serializer.descriptor.serialName
        val expected: String = ListSerializer(elementSerializer)
            .descriptor
            .serialName
        result shouldEqual expected
    }

    @Test
    fun serialization_should_behave_like_a_List() {
        val elements: NotEmptyList<Int> = List(3) { Random.nextInt() }
            .toNotEmptyList()
            .getOrThrow()
        val result: String = Json.encodeToString(elements)
        val expected: String = Json.encodeToString(elements.toList())
        result shouldEqual expected
    }

    @Test
    fun deserialization_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = List(3) { Random.nextInt() }
        val encoded: String = Json.encodeToString(collection)
        val result: NotEmptyList<Int> = Json.decodeFromString(encoded)
        result.toList() contentShouldEqual collection
    }

    @OptIn(InternalKotoolsTypesApi::class)
    @Test
    fun deserialization_should_fail_with_an_empty_Collection() {
        val collection: Collection<Int> = emptyList()
        val encoded: String = Json.encodeToString(collection)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<NotEmptyList<Int>>(encoded)
        }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage = ErrorMessage.emptyCollection
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun serialization_processes_of_wrapped_NotEmptyList_should_pass() {
        @Serializable
        data class Wrapper(val values: NotEmptyList<Int>)

        val wrapper: Wrapper = List(3) { Random.nextInt() }
            .toNotEmptyList()
            .map { Wrapper(it) }
            .getOrThrow()
        val encoded: String = Json.encodeToString(wrapper)
        val decoded: Wrapper = Json.decodeFromString(encoded)
        assertEquals(wrapper.values, decoded.values)
    }
}
