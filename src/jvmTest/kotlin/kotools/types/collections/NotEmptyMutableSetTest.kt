package kotools.types.collections

import kotools.assert.*

class NotEmptyMutableSetTest {
    @Nested
    inner class Constructor {
        @Test
        fun `should return a singleton not empty mutable set`() {
            // GIVEN
            val head = "a"
            // WHEN
            val set: NotEmptyMutableSet<String> = NotEmptyMutableSet(head, head)
            // THEN
            set.run {
                this.head assertEquals head
                size assertEquals 1
            }
        }

        @Test
        fun `should return a not empty mutable set with 3 elements`() {
            // GIVEN
            val head = "a"
            val tail: Array<String> = arrayOf(head, "b", "c")
            // WHEN
            val set: NotEmptyMutableSet<String> =
                NotEmptyMutableSet(head, *tail)
            // THEN
            set.run {
                this.head assertEquals head
                size assertEquals 3
                val expectedSet: Set<String> = setOf(head, *tail)
                forEachIndexed { index: Int, element: String ->
                    element assertEquals expectedSet.elementAt(index)
                }
            }
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class ToNotEmptyMutableSet {
        // ---------- Array ----------

        @Test
        fun `should pass with an array containing at least 1 element`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            // WHEN
            val set: NotEmptyMutableSet<Int> =
                assertPass(array::toNotEmptyMutableSet)
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
            assertFailsWith<IllegalArgumentException>(
                array::toNotEmptyMutableSet
            )
        }

        // ---------- Collection ----------

        @Test
        fun `should pass with a collection containing at least 1 element`() {
            // GIVEN
            val collection: Collection<String> = setOf("a", "a", "b", "c")
            // WHEN
            val set: NotEmptyMutableSet<String> =
                assertPass(collection::toNotEmptyMutableSet)
            // THEN
            set.run {
                head assertEquals collection.first()
                size assertEquals collection.size
                forEachIndexed { index: Int, element: String ->
                    element assertEquals collection.elementAt(index)
                }
            }
        }

        @Test
        fun `should throw an error with an empty collection`() {
            // GIVEN
            val collection: Collection<String> = emptySet()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                collection::toNotEmptyMutableSet
            )
        }
    }

    @Nested
    inner class ToNotEmptyMutableSetOrNull {
        // ---------- Array ----------

        @Test
        fun `should pass with an array containing at least 1 element`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            // WHEN
            val set: NotEmptyMutableSet<Int>? =
                array.toNotEmptyMutableSetOrNull()
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
            val set: NotEmptyMutableSet<Int>? =
                array.toNotEmptyMutableSetOrNull()
            // THEN
            set.assertNull()
        }

        // ---------- Collection ----------

        @Test
        fun `should pass with a collection containing at least 1 element`() {
            // GIVEN
            val collection: Collection<String> = setOf("a", "a", "b", "c")
            // WHEN
            val set: NotEmptyMutableSet<String>? =
                collection.toNotEmptyMutableSetOrNull()
            // THEN
            set.assertNotNull().run {
                head assertEquals collection.first()
                size assertEquals collection.size
                forEachIndexed { index: Int, element: String ->
                    element assertEquals collection.elementAt(index)
                }
            }
        }

        @Test
        fun `should return null with an empty collection`() {
            // GIVEN
            val collection: Collection<String> = emptySet()
            // WHEN
            val set: NotEmptyMutableSet<String>? =
                collection.toNotEmptyMutableSetOrNull()
            // THEN
            set.assertNull()
        }
    }

    @Nested
    inner class ToNotEmptyMutableSetOrElse {
        // ---------- Array ----------

        @Test
        fun `should pass with an array containing at least 1 element`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            val defaultValue: NotEmptyMutableSet<Int> = array.map { -it }
                .run {
                    val head: Int = first()
                    val tail: Array<Int> = subList(1, size).toTypedArray()
                    NotEmptyMutableSet(head, *tail)
                }
            // WHEN
            val set: NotEmptyMutableSet<Int> =
                array.toNotEmptyMutableSetOrElse { defaultValue }
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
        fun `should the default value with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            val defaultValue: NotEmptyMutableSet<Int> =
                NotEmptyMutableSet(-1, -1, -2, -3)
            // WHEN
            val set: NotEmptyMutableSet<Int> =
                array.toNotEmptyMutableSetOrElse { defaultValue }
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
        fun `should pass with a collection containing at least 1 element`() {
            // GIVEN
            val collection: Collection<Int> = setOf(1, 1, 2, 3)
            val defaultValue: NotEmptyMutableSet<Int> = collection.map { -it }
                .run {
                    val head: Int = first()
                    val tail: Array<Int> = subList(1, size).toTypedArray()
                    NotEmptyMutableSet(head, *tail)
                }
            // WHEN
            val set: NotEmptyMutableSet<Int> =
                collection.toNotEmptyMutableSetOrElse { defaultValue }
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
            val collection: Collection<Int> = emptySet()
            val defaultValue: NotEmptyMutableSet<Int> =
                NotEmptyMutableSet(-1, -1, -2, -3)
            // WHEN
            val set: NotEmptyMutableSet<Int> =
                collection.toNotEmptyMutableSetOrElse { defaultValue }
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

    // ---------- Modification operations ----------

    @Nested
    inner class Add {
        @Test
        fun `should return true with a new element`() {
            // GIVEN
            val set: NotEmptyMutableSet<Int> = NotEmptyMutableSet(1, 2, 3)
            val element = 4
            val initialSize: Int = set.size
            // WHEN
            val result: Boolean = set add element
            // THEN
            set.run {
                assertTrue { result && element in this }
                size assertEquals initialSize + 1
            }
        }

        @Test
        fun `should return false with an element that is contained in the set`() {
            // GIVEN
            val set: NotEmptyMutableSet<Int> = NotEmptyMutableSet(1, 2, 3)
            val element: Int = set[1]
            val initialSize: Int = set.size
            // WHEN
            val result: Boolean = set add element
            // THEN
            result.assertFalse()
            set.size assertEquals initialSize
        }
    }

    @Nested
    inner class Remove {
        @Test
        fun `should return true with an element that equals the head`() {
            // GIVEN
            val set: NotEmptyMutableSet<Int> = NotEmptyMutableSet(1, 2, 3)
            val element: Int = set.head
            val initialSize: Int = set.size
            // WHEN
            val result: Boolean = set remove element
            // THEN
            set.run {
                assertTrue { result && element !in this }
                head assertNotEquals element
                size assertEquals initialSize - 1
            }
        }

        @Test
        fun `should return true with an element that is contained in the tail`() {
            // GIVEN
            val set: NotEmptyMutableSet<Int> = NotEmptyMutableSet(1, 2, 3)
            val element: Int = set[1]
            val initialSize: Int = set.size
            // WHEN
            val result: Boolean = set remove element
            // THEN
            set.run {
                assertTrue { result && element !in this }
                size assertEquals initialSize - 1
            }
        }

        @Test
        fun `should return false from a singleton set`() {
            // GIVEN
            val set: NotEmptyMutableSet<Int> = NotEmptyMutableSet(1)
            val element: Int = set.head
            val initialSize: Int = set.size
            // WHEN
            val result: Boolean = set remove element
            // THEN
            result.assertFalse()
            set.run {
                assertTrue { element in this }
                size assertEquals initialSize
            }
        }

        @Test
        fun `should return false with an element that is not present in the set`() {
            // GIVEN
            val set: NotEmptyMutableSet<Int> = NotEmptyMutableSet(1)
            val element = 10
            val initialSize: Int = set.size
            // WHEN
            val result: Boolean = set remove element
            // THEN
            result.assertFalse()
            set.size assertEquals initialSize
        }
    }
}
