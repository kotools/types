package kotools.types.collection

import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.assertHasAMessage
import kotlin.random.Random
import kotlin.test.*

class NotEmptyMapTest {
    @Test
    fun entries_should_return_all_entries_of_this_map_as_a_NotEmptySet() {
        val notEmptyMap: NotEmptyMap<String, Int> = notEmptyMapOf(
            "a" to Random.nextInt(),
            "b" to Random.nextInt(),
            "c" to Random.nextInt()
        )
        assertEquals(
            actual = notEmptyMap.entries.asSet,
            expected = notEmptyMap.asMap.entries
        )
    }

    @Test
    fun keys_should_return_all_keys_of_this_map_as_a_NotEmptySet() {
        val notEmptyMap: NotEmptyMap<String, Int> = notEmptyMapOf(
            "a" to Random.nextInt(),
            "b" to Random.nextInt(),
            "c" to Random.nextInt()
        )
        assertEquals(
            actual = notEmptyMap.keys.asSet,
            expected = notEmptyMap.asMap.keys
        )
    }

    @Test
    fun values_should_return_all_values_of_this_map_as_a_NotEmptyList() {
        val notEmptyMap: NotEmptyMap<String, Int> = notEmptyMapOf(
            "a" to Random.nextInt(),
            "b" to Random.nextInt(),
            "c" to Random.nextInt()
        )
        assertContentEquals(
            actual = notEmptyMap.values.asList,
            expected = notEmptyMap.asMap.values
        )
    }

    @Test
    fun size_should_return_the_size_of_this_map_as_a_StrictlyPositiveInt() {
        val notEmptyMap: NotEmptyMap<String, Int> = notEmptyMapOf(
            "a" to Random.nextInt(),
            "b" to Random.nextInt(),
            "c" to Random.nextInt()
        )
        assertEquals(
            actual = notEmptyMap.size.asInt,
            expected = notEmptyMap.asMap.size
        )
    }

    @Test
    fun toString_should_behave_like_a_Map() {
        val notEmptyMap: NotEmptyMap<String, Int> = notEmptyMapOf(
            "a" to Random.nextInt(),
            "b" to Random.nextInt(),
            "c" to Random.nextInt()
        )
        assertEquals(actual = "$notEmptyMap", expected = "${notEmptyMap.asMap}")
    }

    @Test
    fun notEmptyMapOf_should_pass() {
        val head: Pair<String, Int> = "a" to Random.nextInt()
        val tail: Array<Pair<String, Int>> = arrayOf(
            "b" to Random.nextInt(),
            "c" to Random.nextInt()
        )
        assertEquals(
            actual = notEmptyMapOf(head, *tail).asMap,
            expected = mapOf(head, *tail)
        )
    }

    @Test
    fun map_toNotEmptyMap_should_pass_with_a_not_empty_Map() {
        val map: Map<String, Int> = mapOf(
            "a" to Random.nextInt(),
            "b" to Random.nextInt(),
            "c" to Random.nextInt()
        )
        assertEquals(
            actual = map.asNotEmptyMap.getOrThrow().asMap,
            expected = map
        )
    }

    @Test
    fun map_toNotEmptyMap_should_fail_with_an_empty_Map() {
        val result: Result<NotEmptyMap<String, Int>> = emptyMap<String, Int>()
            .asNotEmptyMap
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .assertHasAMessage()
    }
}

class NotEmptyMapSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_NotEmptyMap_as_serial_name() {
        val keySerializer: KSerializer<String> = String.serializer()
        val valueSerializer: KSerializer<Int> = Int.serializer()
        val result: String = NotEmptyMap
            .serializer(keySerializer, valueSerializer)
            .descriptor
            .serialName
        assertEquals(
            actual = result,
            expected = "${Package.collection}.NotEmptyMap"
        )
    }

    @Test
    fun serialization_should_behave_like_a_Map() {
        val notEmptyMap: NotEmptyMap<String, Int> = notEmptyMapOf(
            "a" to Random.nextInt(),
            "b" to Random.nextInt(),
            "c" to Random.nextInt()
        )
        assertEquals(
            actual = Json.encodeToString(notEmptyMap),
            expected = Json.encodeToString(notEmptyMap.asMap)
        )
    }

    @Test
    fun deserialization_should_pass_with_a_not_empty_Map() {
        val notEmptyMap: NotEmptyMap<String, Int> = notEmptyMapOf(
            "a" to Random.nextInt(),
            "b" to Random.nextInt(),
            "c" to Random.nextInt()
        )
        val encoded: String = Json.encodeToString(notEmptyMap)
        assertEquals(
            actual = Json.decodeFromString<NotEmptyMap<String, Int>>(encoded)
                .asMap,
            expected = notEmptyMap.asMap
        )
    }

    @Test
    fun deserialization_should_fail_with_an_empty_Map() {
        val map: Map<String, Int> = emptyMap()
        val encoded: String = Json.encodeToString(map)
        assertFailsWith<SerializationException> {
            Json.decodeFromString<NotEmptyMap<String, Int>>(encoded)
        }.assertHasAMessage()
    }
}

private fun <K, V> assertEquals(actual: Map<K, V>, expected: Map<K, V>) {
    assertEquals(actual = actual.size, expected = expected.size)
    actual.forEach {
        assertTrue { expected.containsKey(it.key) }
        assertEquals(actual = it.value, expected = expected[it.key])
    }
}
