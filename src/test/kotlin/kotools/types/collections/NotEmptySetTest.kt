package kotools.types.collections

import kotools.assert.*

class NotEmptySetTest {
    @Nested
    inner class Constructor {
        @Test
        fun `should return a singleton not empty set`() {
            // GIVEN
            val head = 1
            // WHEN
            val set: NotEmptySet<Int> = NotEmptySet(head)
            // THEN
            set.head assertEquals head
        }

        @Test
        fun `should return a not empty set with 3 elements`() {
            // GIVEN
            val expectedSet: Set<Int> = setOf(1, 2, 3)
            val head: Int = expectedSet.first()
            val tail: Array<Int> = expectedSet.toList()
                .subList(1, expectedSet.size)
                .toTypedArray()
            // WHEN
            val set: NotEmptySet<Int> = NotEmptySet(head, *tail)
            // THEN
            set.forEachIndexed { index: Int, element: Int ->
                element assertEquals expectedSet.elementAt(index)
            }
        }
    }

    // ---------- Positional access operations ----------

    @Nested
    inner class Get {
        @Test
        fun `should return the first element with an int that equals 0`() {
            // GIVEN
            val set: NotEmptySet<Int> = NotEmptySet(1)
            val index = 0
            // WHEN
            val element: Int = assertPass { set[index] }
            // THEN
            element assertEquals set.head
        }

        @Test
        fun `should return the second element with an int that equals 1`() {
            // GIVEN
            val tail: Array<Int> = arrayOf(2, 3)
            val set: NotEmptySet<Int> = NotEmptySet(1, *tail)
            val index = 1
            // WHEN
            val element: Int = assertPass { set[index] }
            // THEN
            element assertEquals tail[0]
        }

        @Test
        fun `should throw an error with an int that is out of bounds`() {
            // GIVEN
            val set: NotEmptySet<Int> = NotEmptySet(1)
            val index = 10
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> { set[index] }
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class ToNotEmptySet {
        // ---------- Array ----------

        @Test
        fun `should pass with an array with at least 1 element`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            // WHEN
            val set: NotEmptySet<Int> = assertPass(array::toNotEmptySet)
            // THEN
            set.run {
                head assertEquals array.first()
                size assertEquals array.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals array[index]
                }
            }
        }

        @Test
        fun `should throw an error with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(array::toNotEmptySet)
        }

        // ---------- Collection ----------

        @Test
        fun `should pass with a collection with at least 1 element`() {
            // GIVEN
            val collection: Collection<Int> = setOf(1, 1, 2, 3)
            // WHEN
            val set: NotEmptySet<Int> = assertPass(collection::toNotEmptySet)
            // THEN
            set.run {
                head assertEquals collection.first()
                size assertEquals collection.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals collection.elementAt(index)
                }
            }
        }

        @Test
        fun `should throw an error with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptyList()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(collection::toNotEmptySet)
        }
    }

    @Nested
    inner class ToNotEmptySetOrNull {
        // ---------- Array ----------

        @Test
        fun `should pass with an array with at least 1 element`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            // WHEN
            val set: NotEmptySet<Int>? = array.toNotEmptySetOrNull()
            // THEN
            set.assertNotNull().run {
                head assertEquals array.first()
                size assertEquals array.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals array[index]
                }
            }
        }

        @Test
        fun `should return null with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            // WHEN
            val set: NotEmptySet<Int>? = array.toNotEmptySetOrNull()
            // THEN
            set.assertNull()
        }

        // ---------- Collection ----------

        @Test
        fun `should pass with a collection with at least 1 element`() {
            // GIVEN
            val collection: Collection<Int> = setOf(1, 1, 2, 3)
            // WHEN
            val set: NotEmptySet<Int>? = collection.toNotEmptySetOrNull()
            // THEN
            set.assertNotNull().run {
                head assertEquals collection.first()
                size assertEquals collection.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals collection.elementAt(index)
                }
            }
        }

        @Test
        fun `should return null with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptySet()
            // WHEN
            val set: NotEmptySet<Int>? = collection.toNotEmptySetOrNull()
            // THEN
            set.assertNull()
        }
    }

    @Nested
    inner class ToNotEmptySetOrElse {
        // ---------- Array ----------

        @Test
        fun `should pass with an array with at least 1 element`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            val defaultValue: NotEmptySet<Int> = array.map { -it }
                .run {
                    val head: Int = first()
                    val tail: Array<Int> = subList(1, size).toTypedArray()
                    NotEmptySet(head, *tail)
                }
            // WHEN
            val set: NotEmptySet<Int> =
                array toNotEmptySetOrElse { defaultValue }
            // THEN
            set.run {
                head.run {
                    assertEquals(array.first())
                    assertNotEquals(defaultValue.head)
                }
                size.run {
                    assertEquals(array.size)
                    assertEquals(defaultValue.size)
                }
                forEachIndexed { index: Int, element: Int ->
                    element.run {
                        assertEquals(array[index])
                        assertNotEquals(defaultValue[index])
                    }
                }
            }
        }

        @Test
        fun `should return the default value with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            val defaultValue: NotEmptySet<Int> = NotEmptySet(-1, -2, -3)
            // WHEN
            val set: NotEmptySet<Int> =
                array toNotEmptySetOrElse { defaultValue }
            // THEN
            set.run {
                head assertEquals defaultValue.head
                size assertEquals defaultValue.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals defaultValue[index]
                }
            }
        }

        // ---------- Collection ----------

        @Test
        fun `should pass with a collection with at least 1 element`() {
            // GIVEN
            val collection: Collection<Int> = setOf(1, 1, 2, 3)
            val defaultValue: NotEmptySet<Int> = collection.map { -it }
                .run {
                    val head: Int = first()
                    val tail: Array<Int> = subList(1, size).toTypedArray()
                    NotEmptySet(head, *tail)
                }
            // WHEN
            val set: NotEmptySet<Int> =
                collection toNotEmptySetOrElse { defaultValue }
            // THEN
            set.run {
                head.run {
                    assertEquals(collection.first())
                    assertNotEquals(defaultValue.head)
                }
                size.run {
                    assertEquals(collection.size)
                    assertEquals(defaultValue.size)
                }
                forEachIndexed { index: Int, element: Int ->
                    element.run {
                        assertEquals(collection.elementAt(index))
                        assertNotEquals(defaultValue[index])
                    }
                }
            }
        }

        @Test
        fun `should return the default value with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptyList()
            val defaultValue: NotEmptySet<Int> = NotEmptySet(-1, -2, -3)
            // WHEN
            val set: NotEmptySet<Int> =
                collection toNotEmptySetOrElse { defaultValue }
            // THEN
            set.run {
                head assertEquals defaultValue.head
                size assertEquals defaultValue.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals defaultValue[index]
                }
            }
        }
    }
}
