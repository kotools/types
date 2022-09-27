package kotools.types.collections

import kotools.assert.*

class NotEmptyListJvmTest {
    @Nested
    inner class Constructor {
        @Test
        fun `should return a singleton list`() {
            // GIVEN
            val head = 1
            // WHEN
            val list: NotEmptyListJvm<Int> = NotEmptyListJvm(head)
            // THEN
            list.head assertEquals head
        }

        @Test
        fun `should return a list with 3 elements`() {
            // GIVEN
            val expectedList: List<Int> = listOf(1, 2, 3)
            val head: Int = expectedList[0]
            val tail: Array<Int> = expectedList.subList(1, expectedList.size)
                .toTypedArray()
            // WHEN
            val list: NotEmptyListJvm<Int> = NotEmptyListJvm(head, *tail)
            // THEN
            list.forEachIndexed { index: Int, element: Int ->
                element assertEquals expectedList[index]
            }
        }
    }

    // ---------- Positional access operations ----------

    @Nested
    inner class Get {
        // ---------- Int ----------

        @Test
        fun `should return the first element with an int that equals 0`() {
            // GIVEN
            val head = 1
            val list: NotEmptyListJvm<Int> = NotEmptyListJvm(head, 2)
            val index = 0
            // WHEN
            val element: Int = assertPass { list[index] }
            // THEN
            element assertEquals head
        }

        @Test
        fun `should return the second element with an int that equals 1`() {
            // GIVEN
            val expectedElement = 2
            val list: NotEmptyListJvm<Int> = NotEmptyListJvm(1, expectedElement)
            val index = 1
            // WHEN
            val element: Int = assertPass { list[index] }
            // THEN
            element assertEquals expectedElement
        }

        @Test
        fun `should throw an error with an int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyListJvm<Int> = NotEmptyListJvm(1)
            val index = 10
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> { list[index] }
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class ArrayToNotEmptyList {
        @Test
        fun `should pass with an array containing 3 elements`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            // WHEN
            val list: NotEmptyListJvm<Int> =
                assertPass(array::toNotEmptyListJvm)
            // THEN
            list.forEachIndexed { index: Int, element: Int ->
                element assertEquals array[index]
            }
        }

        @Test
        fun `should throw an error with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                block = array::toNotEmptyListJvm
            )
        }
    }

    @Nested
    inner class CollectionToNotEmptyList {
        @Test
        fun `should pass with a collection containing 3 elements`() {
            // GIVEN
            val collection: Collection<Int> = listOf(1, 2, 3)
            // WHEN
            val list: NotEmptyListJvm<Int> =
                assertPass(collection::toNotEmptyListJvm)
            // THEN
            collection.forEachIndexed { index: Int, element: Int ->
                element assertEquals list[index]
            }
        }

        @Test
        fun `should throw an error with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptyList()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                block = collection::toNotEmptyListJvm
            )
        }
    }

    @Nested
    inner class ArrayToNotEmptyListOrNull {
        @Test
        fun `should pass with an array containing 3 elements`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            // WHEN
            val list: NotEmptyListJvm<Int>? = array.toNotEmptyListJvmOrNull()
            // THEN
            list.assertNotNull().forEachIndexed { index: Int, element: Int ->
                element assertEquals array[index]
            }
        }

        @Test
        fun `should return null with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            // WHEN
            val list: NotEmptyListJvm<Int>? = array.toNotEmptyListJvmOrNull()
            // THEN
            list.assertNull()
        }
    }

    @Nested
    inner class CollectionToNotEmptyListOrNull {
        @Test
        fun `should pass with a collection containing 3 elements`() {
            // GIVEN
            val collection: Collection<Int> = setOf(1, 2, 3)
            // WHEN
            val list: NotEmptyListJvm<Int>? =
                collection.toNotEmptyListJvmOrNull()
            // THEN
            list.assertNotNull().let {
                collection.forEachIndexed { index: Int, element: Int ->
                    element assertEquals it[index]
                }
            }
        }

        @Test
        fun `should return null with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptySet()
            // WHEN
            val list: NotEmptyListJvm<Int>? =
                collection.toNotEmptyListJvmOrNull()
            // THEN
            list.assertNull()
        }
    }

    @Nested
    inner class ArrayToNotEmptyListOrElse {
        @Test
        fun `should pass with an array containing 3 elements`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            val defaultValue = NotEmptyListJvm(-1, -2, -3)
            // WHEN
            val list: NotEmptyListJvm<Int> =
                array toNotEmptyListJvmOrElse { defaultValue }
            // THEN
            list.forEachIndexed { index: Int, element: Int ->
                element assertEquals array[index]
                element assertNotEquals defaultValue[index]
            }
        }

        @Test
        fun `should return the default value with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            val defaultValue = NotEmptyListJvm(-1, -2, -3)
            // WHEN
            val list: NotEmptyListJvm<Int> =
                array toNotEmptyListJvmOrElse { defaultValue }
            // THEN
            list.forEachIndexed { index: Int, element: Int ->
                element assertEquals defaultValue[index]
            }
        }
    }

    @Nested
    inner class CollectionToNotEmptyListOrElse {
        @Test
        fun `should pass with a collection containing 3 elements`() {
            // GIVEN
            val collection: Collection<Int> = listOf(1, 2, 3)
            val defaultValue = NotEmptyListJvm(-1, -2, -3)
            // WHEN
            val list: NotEmptyListJvm<Int> =
                collection toNotEmptyListJvmOrElse { defaultValue }
            // THEN
            collection.forEachIndexed { index: Int, element: Int ->
                element assertEquals list[index]
                element assertNotEquals defaultValue[index]
            }
        }

        @Test
        fun `should return the default value with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptyList()
            val defaultValue = NotEmptyListJvm(-1, -2, -3)
            // WHEN
            val list: NotEmptyListJvm<Int> =
                collection toNotEmptyListJvmOrElse { defaultValue }
            // THEN
            list.forEachIndexed { index: Int, element: Int ->
                element assertEquals defaultValue[index]
            }
        }
    }
}
