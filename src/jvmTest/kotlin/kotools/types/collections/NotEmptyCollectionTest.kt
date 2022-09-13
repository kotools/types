package kotools.types.collections

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*
import kotools.types.number.NonZeroIntJvm
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.string.NotBlankStringJvm

class NotEmptyCollectionTest {
    // ---------- Query operations ----------

    @Nested
    inner class Size {
        // ---------- NotEmptyList ----------

        @Test
        fun `should return 1 with a singleton list`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptyList(1)
            // WHEN & THEN
            collection.size assertEquals 1
        }

        @Test
        fun `should return 3 with a not empty list containing 3 elements`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptyList(1, 2, 3)
            // WHEN & THEN
            collection.size assertEquals 3
        }

        // ---------- NotEmptySet ----------

        @Test
        fun `should return 1 with a singleton set`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptySet(1)
            // WHEN & THEN
            collection.size assertEquals 1
        }

        @Test
        fun `should return 3 with a not empty set containing 3 elements`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptySet(1, 2, 3)
            // WHEN & THEN
            collection.size assertEquals 3
        }
    }

    @Nested
    inner class TypedSize {
        // ---------- NotEmptyList ----------

        @Test
        fun `should return 1 as a strictly positive int with a singleton list`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptyList(1)
            // WHEN
            val size: StrictlyPositiveInt = collection.typedSize
            // THEN
            size.value assertEquals 1
        }

        @Test
        fun `should return 3 as a strictly positive int with a list of 3 elements`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptyList(1, 2, 3)
            // WHEN
            val size: StrictlyPositiveInt = collection.typedSize
            // THEN
            size.value assertEquals 3
        }

        // ---------- NotEmptySet ----------

        @Test
        fun `should return 1 as a strictly positive int with a singleton set`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptySet(1)
            // WHEN
            val size: StrictlyPositiveInt = collection.typedSize
            // THEN
            size.value assertEquals 1
        }

        @Test
        fun `should return 3 as a strictly positive int with a set of 3 elements`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptySet(1, 2, 3)
            // WHEN
            val size: StrictlyPositiveInt = collection.typedSize
            // THEN
            size.value assertEquals 3
        }
    }

    @Nested
    inner class IsEmpty {
        // ---------- NotEmptyList ----------

        @Test
        fun `should return false with a not empty list`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptyList(1, 2, 3)
            // WHEN & THEN
            assertFalse(block = collection::isEmpty)
        }

        // ---------- NotEmptySet ----------

        @Test
        fun `should return false with a not empty set`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptySet(1, 2, 3)
            // WHEN & THEN
            assertFalse(block = collection::isEmpty)
        }
    }

    // ---------- Positional access operations ----------

    @Nested
    inner class Get {
        // ---------- PositiveInt ----------

        @Test
        fun `should return the first element with a positive int that equals 0`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptyList(1, 2)
            val index = PositiveInt(0)
            // WHEN
            val element: Int = assertPass { collection[index] }
            // THEN
            element assertEquals collection.head
        }

        @Test
        fun `should return the second element with a positive int that equals 1`() {
            // GIVEN
            val tail = 2
            val collection: NotEmptyCollection<Int> = NotEmptyList(1, tail)
            val index = PositiveInt(1)
            // WHEN
            val element: Int = assertPass { collection[index] }
            // THEN
            element assertEquals tail
        }

        @Test
        fun `should throw an error with a positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyList<Int> = NotEmptyList(1)
            val index = PositiveInt(10)
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> { list[index] }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return the second element with a strictly positive int that equals 1`() {
            // GIVEN
            val tail = 2
            val collection: NotEmptyCollection<Int> = NotEmptyList(1, tail)
            val index = StrictlyPositiveInt(1)
            // WHEN
            val element: Int = assertPass { collection[index] }
            // THEN
            element assertEquals tail
        }

        @Test
        fun `should throw an error with a strictly positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyList<Int> = NotEmptyList(1)
            val index = StrictlyPositiveInt(10)
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> { list[index] }
        }
    }

    @Nested
    inner class GetOrNull {
        // ---------- Int ----------

        @Test
        fun `should return the first element with an int that equals 0`() {
            // GIVEN
            val head = 1
            val collection: NotEmptyCollection<Int> = NotEmptyList(head)
            val index = 0
            // WHEN
            val element: Int? = collection getOrNull index
            // THEN
            element.assertNotNull().run {
                assertEquals(head)
                assertEquals(collection.head)
            }
        }

        @Test
        fun `should return the second element with an int that equals 1`() {
            // GIVEN
            val tail = 2
            val collection: NotEmptyCollection<Int> = NotEmptyList(1, tail)
            val index = 1
            // WHEN
            val element: Int? = collection getOrNull index
            // THEN
            element.assertNotNull() assertEquals tail
        }

        @Test
        fun `should return null with an int that is out of bounds`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptyList(1)
            val index = 10
            // WHEN
            val element: Int? = collection getOrNull index
            // THEN
            element.assertNull()
        }

        // ---------- PositiveInt ----------

        @Test
        fun `should return the first element with a positive int that equals 0`() {
            // GIVEN
            val head = 1
            val collection: NotEmptyCollection<Int> = NotEmptyList(head, 2)
            val index = PositiveInt(0)
            // WHEN
            val element: Int? = collection getOrNull index
            // THEN
            element.assertNotNull().run {
                assertEquals(head)
                assertEquals(collection.head)
            }
        }

        @Test
        fun `should return the second element with a positive int that equals 1`() {
            // GIVEN
            val tail = 2
            val collection: NotEmptyCollection<Int> = NotEmptyList(1, tail)
            val index = PositiveInt(1)
            // WHEN
            val element: Int? = collection getOrNull index
            // THEN
            element.assertNotNull() assertEquals tail
        }

        @Test
        fun `should return null with a positive int that is out of bounds`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptyList(1)
            val index = PositiveInt(10)
            // WHEN
            val element: Int? = collection getOrNull index
            // THEN
            element.assertNull()
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return the second element with a strictly positive int that equals 1`() {
            // GIVEN
            val tail = 2
            val collection: NotEmptyCollection<Int> = NotEmptyList(1, tail)
            val index = StrictlyPositiveInt(1)
            // WHEN
            val element: Int? = collection getOrNull index
            // THEN
            element.assertNotNull() assertEquals tail
        }

        @Test
        fun `should return null with a strictly positive int that is out of bounds`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptyList(1)
            val index = StrictlyPositiveInt(10)
            // WHEN
            val element: Int? = collection getOrNull index
            // THEN
            element.assertNull()
        }
    }

    @Nested
    inner class GetOrElse {
        // ---------- Int ----------

        @Test
        fun `should return the first element with an int that equals 0`() {
            // GIVEN
            val head = 1
            val collection: NotEmptyCollection<Int> = NotEmptyList(head)
            val index = 0
            val defaultValue: Int = -head
            // WHEN
            val element: Int = collection.getOrElse(index) { defaultValue }
            // THEN
            element.run {
                assertEquals(head)
                assertEquals(collection.head)
                assertNotEquals(defaultValue)
            }
        }

        @Test
        fun `should return the second element with an int that equals 1`() {
            // GIVEN
            val tail = 2
            val collection: NotEmptyCollection<Int> = NotEmptyList(1, tail)
            val index = 1
            val defaultValue: Int = -tail
            // WHEN
            val element: Int = collection.getOrElse(index) { defaultValue }
            // THEN
            element.run {
                assertEquals(tail)
                assertNotEquals(defaultValue)
            }
        }

        @Test
        fun `should return the default value with an int that is out of bounds`() {
            // GIVEN
            val head = 1
            val collection: NotEmptyCollection<Int> = NotEmptyList(head)
            val index = 10
            val defaultValue: Int = -head
            // WHEN
            val element: Int = collection.getOrElse(index) { defaultValue }
            // THEN
            element assertEquals defaultValue
        }

        // ---------- PositiveInt ----------

        @Test
        fun `should return the first element with a positive int that equals 0`() {
            // GIVEN
            val head = 1
            val collection: NotEmptyCollection<Int> = NotEmptyList(head, 2)
            val index = PositiveInt(0)
            val defaultValue = -1
            // WHEN
            val element: Int = collection.getOrElse(index) { defaultValue }
            // THEN
            element.run {
                assertEquals(head)
                assertEquals(collection.head)
                assertNotEquals(defaultValue)
            }
        }

        @Test
        fun `should return the second element with a positive int that equals 1`() {
            // GIVEN
            val tail = 2
            val collection: NotEmptyCollection<Int> = NotEmptyList(1, tail)
            val index = PositiveInt(1)
            val defaultValue = -1
            // WHEN
            val element: Int = collection.getOrElse(index) { defaultValue }
            // THEN
            element.run {
                assertEquals(tail)
                assertNotEquals(defaultValue)
            }
        }

        @Test
        fun `should return the default value with a positive int that is out of bounds`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptyList(1)
            val index = PositiveInt(10)
            val defaultValue = -1
            // WHEN
            val element: Int = collection.getOrElse(index) { defaultValue }
            // THEN
            element assertEquals defaultValue
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return the second element with a strictly positive int that equals 1`() {
            // GIVEN
            val tail = 2
            val collection: NotEmptyCollection<Int> = NotEmptyList(1, tail)
            val index = StrictlyPositiveInt(1)
            val defaultValue = -1
            // WHEN
            val element: Int = collection.getOrElse(index) { defaultValue }
            // THEN
            element.run {
                assertEquals(tail)
                assertNotEquals(defaultValue)
            }
        }

        @Test
        fun `should return the default value with a strictly positive int that is out of bounds`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptyList(1)
            val index = StrictlyPositiveInt(10)
            val defaultValue = -1
            // WHEN
            val element: Int = collection.getOrElse(index) { defaultValue }
            // THEN
            element assertEquals defaultValue
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class ToNotBlankString {
        @Test
        fun `should return its string representation as a not blank string`() {
            // GIVEN
            val collection: NotEmptyCollection<Int> = NotEmptyList(1)
            // WHEN
            val string: NotBlankStringJvm = collection.toNotBlankString()
            // THEN
            string.value assertEquals collection.toString()
        }
    }
}

class SealedNotEmptyCollectionSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun `should have the correct descriptor`() {
        // GIVEN
        val serializer: NotEmptyCollectionSerializer<NonZeroIntJvm> =
            NotEmptyCollectionSerializer(NonZeroIntJvm.Serializer)
        // WHEN
        val result: String = serializer.descriptor.serialName
        // THEN
        result assertEquals NotEmptyCollection::class.qualifiedName
    }

    @Test
    fun `should serialize correctly a not empty list`() {
        // GIVEN
        val list: NotEmptyList<Int> = NotEmptyList(1, 1)
        // WHEN
        val result: String = Json.encodeToString(list)
        // THEN
        result assertEquals "$list".replace(" ", "")
    }

    @Test
    fun `should serialize correctly a not empty mutable list`() {
        // GIVEN
        val mutableList: NotEmptyMutableList<Int> = NotEmptyMutableList(1, 1)
        // WHEN
        val result: String = Json.encodeToString(mutableList)
        // THEN
        result assertEquals "$mutableList".replace(" ", "")
    }

    @Test
    fun `should serialize correctly a not empty set`() {
        // GIVEN
        val set: NotEmptySet<Int> = NotEmptySet(1, 1)
        // WHEN
        val result: String = Json.encodeToString(set)
        // THEN
        result assertEquals "$set".replace(" ", "")
    }

    @Test
    fun `should serialize correctly a not empty mutable set`() {
        // GIVEN
        val mutableSet: NotEmptyMutableSet<Int> = NotEmptyMutableSet(1, 1)
        // WHEN
        val result: String = Json.encodeToString(mutableSet)
        // THEN
        result assertEquals "$mutableSet".replace(" ", "")
    }

    @Test
    fun `should deserialize correctly to a not empty list`() {
        // GIVEN
        val x = 1
        val string = "[$x,$x]"
        // WHEN
        val result: NotEmptyList<Int> = Json.decodeFromString(string)
        // THEN
        result.size assertEquals 2
        x.run {
            assertEquals(result.head)
            assertEquals(result[1])
        }
    }

    @Test
    fun `should deserialize correctly to a not empty mutable list`() {
        // GIVEN
        val x = 1
        val string = "[$x,$x]"
        // WHEN
        val result: NotEmptyMutableList<Int> = Json.decodeFromString(string)
        // THEN
        result.size assertEquals 2
        x.run {
            assertEquals(result.head)
            assertEquals(result[1])
        }
    }

    @Test
    fun `should deserialize correctly to a not empty set`() {
        // GIVEN
        val x = 1
        val string = "[$x,$x]"
        // WHEN
        val result: NotEmptySet<Int> = Json.decodeFromString(string)
        // THEN
        result.run {
            size assertEquals 1
            head assertEquals x
        }
    }

    @Test
    fun `should deserialize correctly to a not empty mutable set`() {
        // GIVEN
        val x = 1
        val string = "[$x,$x]"
        // WHEN
        val result: NotEmptyMutableSet<Int> = Json.decodeFromString(string)
        // THEN
        result.run {
            size assertEquals 1
            head assertEquals x
        }
    }
}
