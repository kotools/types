package kotools.types.collections

import kotools.assert.*
import kotools.types.core.indexOutOfBoundsMessage
import kotools.types.int.PositiveInt
import kotools.types.int.StrictlyPositiveInt

class NotEmptyMutableListJvmTest {
    @Nested
    inner class Constructor {
        @Test
        fun `should return a singleton list`() {
            // GIVEN
            val head = 1
            // WHEN
            val list: NotEmptyMutableListJvm<Int> = NotEmptyMutableListJvm(head)
            // THEN
            list.head assertEquals head
        }

        @Test
        fun `should return a list with 3 elements`() {
            // GIVEN
            val expectedList: List<Int> = listOf(1, 2, 3)
            val head: Int = expectedList.first()
            val tail: Array<Int> = expectedList.subList(1, expectedList.size)
                .toTypedArray()
            // WHEN
            val list: NotEmptyMutableListJvm<Int> =
                NotEmptyMutableListJvm(head, *tail)
            // THEN
            list.run {
                size assertEquals expectedList.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals expectedList[index]
                }
            }
        }
    }

    // ---------- Positional access operations ----------

    @Nested
    inner class Add {
        // ---------- Int ----------

        @Test
        fun `should insert an element at the beginning of the list with an index as an int that equals 0`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("two")
            val index = 0
            val element = "one"
            // WHEN
            assertPass { list.add(index, element) }
            // THEN
            list.head assertEquals element
            list[index] assertEquals element
        }

        @Test
        fun `should insert an element at the end of the list with an index as an int that equals the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index: Int = list.size
            val element = "two"
            // WHEN
            assertPass { list.add(index, element) }
            // THEN
            list[index] assertEquals element
        }

        @Test
        fun `should insert an element into the list with an index as an int in 1 until the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one", "three")
            val index = 1
            val element = "two"
            // WHEN
            assertPass { list.add(index, element) }
            // THEN
            list[index] assertEquals element
        }

        @Test
        fun `should throw an error with an index as an int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index: Int = list.size + 1
            val element = "two"
            // WHEN
            val error: IndexOutOfBoundsException =
                assertFailsWith { list.add(index, element) }
            // THEN
            error.message.assertNotNull() assertEquals indexOutOfBoundsMessage(
                index,
                list.size
            )
        }

        // ---------- PositiveInt ----------

        @Test
        fun `should insert an element at the beginning of the list with an index as a positive int that equals 0`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("two")
            val index = PositiveInt(0)
            val element = "one"
            // WHEN
            assertPass { list.add(index, element) }
            // THEN
            element.run {
                assertEquals(list.head)
                assertEquals(list[index])
            }
        }

        @Test
        fun `should insert an element at the end of the list with an index as a positive int that equals the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index = PositiveInt(list.size)
            val element = "two"
            // WHEN
            assertPass { list.add(index, element) }
            // THEN
            list[index] assertEquals element
        }

        @Test
        fun `should insert an element into the list with an index as a positive int in 1 until the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one", "three")
            val index = PositiveInt(1)
            val element = "two"
            // WHEN
            assertPass { list.add(index, element) }
            // THEN
            list[index] assertEquals element
        }

        @Test
        fun `should throw an error with an index as a positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index = PositiveInt(list.size + 1)
            val element = "two"
            // WHEN
            val error: IndexOutOfBoundsException =
                assertFailsWith { list.add(index, element) }
            // THEN
            error.message.assertNotNull() assertEquals indexOutOfBoundsMessage(
                index.value,
                list.size
            )
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should insert an element at the end of the list with an index as a strictly positive int that equals the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index: StrictlyPositiveInt = list.typedSize
            val element = "two"
            // WHEN
            assertPass { list.add(index, element) }
            // THEN
            list[index] assertEquals element
        }

        @Test
        fun `should insert an element into the list with an index as a strictly positive int in 1 until the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one", "three")
            val index = StrictlyPositiveInt(1)
            val element = "two"
            // WHEN
            assertPass { list.add(index, element) }
            // THEN
            list[index] assertEquals element
        }

        @Test
        fun `should throw an error with an index as a strictly positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index = StrictlyPositiveInt(list.size + 1)
            val element = "two"
            // WHEN
            val error: IndexOutOfBoundsException =
                assertFailsWith { list.add(index, element) }
            // THEN
            error.message.assertNotNull() assertEquals indexOutOfBoundsMessage(
                index.value,
                list.size
            )
        }
    }

    @Nested
    inner class AddOrNull {
        // ---------- Int ----------

        @Test
        fun `should insert an element at the beginning of the list with an index as an int that equals 0`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("two")
            val index = 0
            val element = "one"
            // WHEN
            val result: Unit? = list.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            element.run {
                assertEquals(list.head)
                assertEquals(list[index])
            }
        }

        @Test
        fun `should insert an element at the end of the list with an index as an int that equals the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index: Int = list.size
            val element = "two"
            // WHEN
            val result: Unit? = list.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            list[index] assertEquals element
        }

        @Test
        fun `should insert an element into the list with an index as an int in 1 until the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one", "three")
            val index = 1
            val element = "two"
            // WHEN
            val result: Unit? = list.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            list[index] assertEquals element
        }

        @Test
        fun `should return null with an index as an int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index: Int = list.size + 1
            val element = "two"
            // WHEN
            val result: Unit? = list.addOrNull(index, element)
            // THEN
            result.assertNull()
        }

        // ---------- PositiveInt ----------

        @Test
        fun `should insert an element at the beginning of the list with an index as a positive int that equals 0`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("two")
            val index = PositiveInt(0)
            val element = "one"
            // WHEN
            val result: Unit? = list.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            element.run {
                assertEquals(list.head)
                assertEquals(list[index])
            }
        }

        @Test
        fun `should insert an element at the end of the list with an index as a positive int that equals the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index = PositiveInt(list.size)
            val element = "two"
            // WHEN
            val result: Unit? = list.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            list[index] assertEquals element
        }

        @Test
        fun `should insert an element into the list with an index as a positive int in 1 until the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one", "three")
            val index = PositiveInt(1)
            val element = "two"
            // WHEN
            val result: Unit? = list.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            list[index] assertEquals element
        }

        @Test
        fun `should return null with an index as a positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index = PositiveInt(list.size + 1)
            val element = "two"
            // WHEN
            val result: Unit? = list.addOrNull(index, element)
            // THEN
            result.assertNull()
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should insert an element at the end of the list with an index as a strictly positive int that equals the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index: StrictlyPositiveInt = list.typedSize
            val element = "two"
            // WHEN
            val result: Unit? = list.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            list[index] assertEquals element
        }

        @Test
        fun `should insert an element into the list with an index as a strictly positive int in 1 until the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one", "three")
            val index = StrictlyPositiveInt(1)
            val element = "two"
            // WHEN
            val result: Unit? = list.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            list[index] assertEquals element
        }

        @Test
        fun `should return null with an index as a strictly positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index = StrictlyPositiveInt(list.size + 1)
            val element = "two"
            // WHEN
            val result: Unit? = list.addOrNull(index, element)
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class Get {
        @Test
        fun `should return the first element with an int that equals 0`() {
            // GIVEN
            val head = 1
            val list: NotEmptyMutableListJvm<Int> =
                NotEmptyMutableListJvm(head, 2)
            val index = 0
            // WHEN
            val element: Int = assertPass { list[index] }
            // THEN
            element assertEquals head
        }

        @Test
        fun `should return the second element with an int that equals 1`() {
            // GIVEN
            val tail = 2
            val list: NotEmptyMutableListJvm<Int> =
                NotEmptyMutableListJvm(1, tail)
            val index = 1
            // WHEN
            val element: Int = assertPass { list[index] }
            // THEN
            element assertEquals tail
        }

        @Test
        fun `should throw an error with an int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<Int> = NotEmptyMutableListJvm(1)
            val index = 10
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> { list[index] }
        }
    }

    @Nested
    inner class RemoveAt {
        // ---------- Int ----------

        @Test
        fun `should remove the head from a list containing several elements and with an index as an int that equals 0`() {
            // GIVEN
            val head = "one"
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm(head, "two")
            val index = 0
            // WHEN
            val element: String = assertPass { list removeAt index }
            // THEN
            element.run {
                assertEquals(head)
                assertNotEquals(list.head)
                assertFalse { this in list }
            }
        }

        @Test
        fun `shouldn't remove the head from a singleton list and with an index as an int that equals 0`() {
            // GIVEN
            val head = "one"
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm(head)
            val index = 0
            // WHEN
            val element: String = assertPass { list removeAt index }
            // THEN
            element.run {
                assertEquals(head)
                assertEquals(list.head)
                assertTrue { this in list }
            }
        }

        @Test
        fun `should remove an element with an index as an int in 1 until the list's size`() {
            // GIVEN
            val tail = "two"
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one", tail)
            val index: Int = list.size - 1
            // WHEN
            val element: String = assertPass { list removeAt index }
            // THEN
            element assertEquals tail
            assertFalse { element in list }
        }

        @Test
        fun `should throw an error with an index as an int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index: Int = list.size
            // WHEN
            val error: IndexOutOfBoundsException =
                assertFailsWith { list removeAt index }
            // THEN
            error.message.assertNotNull() assertEquals indexOutOfBoundsMessage(
                index,
                list.size
            )
        }

        // ---------- PositiveInt ----------

        @Test
        fun `should remove the head from a list containing several elements and with an index as a positive int that equals 0`() {
            // GIVEN
            val head = "one"
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm(head, "two")
            val index = PositiveInt(0)
            // WHEN
            val element: String = assertPass { list removeAt index }
            // THEN
            element.run {
                assertEquals(head)
                assertNotEquals(list.head)
                assertFalse { this in list }
            }
        }

        @Test
        fun `should remove the head from a singleton list and with an index as a positive int that equals 0`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index = PositiveInt(0)
            // WHEN
            val element: String = assertPass { list removeAt index }
            // THEN
            element.run {
                assertEquals(list.head)
                assertTrue { element in list }
            }
        }

        @Test
        fun `should remove an element with an index as a positive int in 1 until the list's size`() {
            // GIVEN
            val tail = "two"
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one", tail)
            val index = PositiveInt(list.size - 1)
            // WHEN
            val element: String = assertPass { list removeAt index }
            // THEN
            element.run {
                assertEquals(tail)
                assertFalse { this in list }
            }
        }

        @Test
        fun `should throw an error with an index as a positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index = PositiveInt(list.size)
            // WHEN
            val error: IndexOutOfBoundsException =
                assertFailsWith { list removeAt index }
            // THEN
            error.message.assertNotNull() assertEquals indexOutOfBoundsMessage(
                index.value,
                list.size
            )
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should remove an element with an index as a strictly positive int in 1 until the list's size`() {
            // GIVEN
            val tail = "two"
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one", tail)
            val index = StrictlyPositiveInt(list.size - 1)
            // WHEN
            val element: String = assertPass { list removeAt index }
            // THEN
            element.run {
                assertEquals(tail)
                assertFalse { this in list }
            }
        }

        @Test
        fun `should throw an error with an index as a strictly positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index: StrictlyPositiveInt = list.typedSize
            // WHEN
            val error: IndexOutOfBoundsException =
                assertFailsWith { list removeAt index }
            // THEN
            error.message.assertNotNull() assertEquals indexOutOfBoundsMessage(
                index.value,
                list.size
            )
        }
    }

    @Nested
    inner class RemoveAtOrNull {
        // ---------- Int ----------

        @Test
        fun `should remove the head from a list containing several elements and with an index as an int that equals 0`() {
            // GIVEN
            val head = "one"
            val list = NotEmptyMutableListJvm(head, "two")
            val index = 0
            // WHEN
            val element: String? = list removeAtOrNull index
            // THEN
            element.assertNotNull().run {
                assertEquals(head)
                assertNotEquals(list.head)
                assertFalse { this in list }
            }
        }

        @Test
        fun `shouldn't remove the head from a singleton list and with an index as an int that equals 0`() {
            // GIVEN
            val head = "one"
            val list = NotEmptyMutableListJvm(head)
            val index = 0
            // WHEN
            val element: String? = list removeAtOrNull index
            // THEN
            element.assertNotNull().run {
                assertEquals(head)
                assertEquals(list.head)
                assertTrue { this in list }
            }
        }

        @Test
        fun `should remove an element with an index as an int in 1 until the list's size`() {
            // GIVEN
            val tail = "two"
            val list = NotEmptyMutableListJvm("one", tail)
            val index = 1
            // WHEN
            val element: String? = list removeAtOrNull index
            // THEN
            element.assertNotNull().run {
                assertEquals(tail)
                assertFalse { this in list }
            }
        }

        @Test
        fun `should return null with an index as an int that is out of bounds`() {
            // GIVEN
            val list = NotEmptyMutableListJvm("one")
            val index: Int = list.size
            // WHEN
            val element: String? = list removeAtOrNull index
            // THEN
            element.assertNull()
        }

        // ---------- PositiveInt ----------

        @Test
        fun `should remove the head from a list containing several elements and with an index as a positive int that equals 0`() {
            // GIVEN
            val head = "one"
            val list = NotEmptyMutableListJvm(head, "two")
            val index = PositiveInt(0)
            // WHEN
            val element: String? = list removeAtOrNull index
            // THEN
            element.assertNotNull().run {
                assertEquals(head)
                assertNotEquals(list.head)
                assertFalse { this in list }
            }
        }

        @Test
        fun `shouldn't remove the head from a singleton list and with an index as a positive int that equals 0`() {
            // GIVEN
            val head = "one"
            val list = NotEmptyMutableListJvm(head)
            val index = PositiveInt(0)
            // WHEN
            val element: String? = list removeAtOrNull index
            // THEN
            element.assertNotNull().run {
                assertEquals(head)
                assertEquals(list.head)
                assertTrue { this in list }
            }
        }

        @Test
        fun `should remove an element with an index as a positive int in 1 until the list's size`() {
            // GIVEN
            val tail = "two"
            val list = NotEmptyMutableListJvm("one", tail)
            val index = PositiveInt(list.size - 1)
            // WHEN
            val element: String? = list removeAtOrNull index
            // THEN
            element.assertNotNull().run {
                assertEquals(tail)
                assertFalse { this in list }
            }
        }

        @Test
        fun `should return null with an index as a positive int that is out of bounds`() {
            // GIVEN
            val list = NotEmptyMutableListJvm("one")
            val index = PositiveInt(list.size)
            // WHEN
            val element: String? = list removeAtOrNull index
            // THEN
            element.assertNull()
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should remove an element with an index as a strictly positive int in 1 until the list's size`() {
            // GIVEN
            val tail = "two"
            val list = NotEmptyMutableListJvm("one", tail)
            val index = StrictlyPositiveInt(list.size - 1)
            // WHEN
            val element: String? = list removeAtOrNull index
            // THEN
            element.assertNotNull().run {
                assertEquals(tail)
                assertFalse { this in list }
            }
        }

        @Test
        fun `should return null with an index as a strictly positive int that is out of bounds`() {
            // GIVEN
            val list = NotEmptyMutableListJvm("one")
            val index: StrictlyPositiveInt = list.typedSize
            // WHEN
            val element: String? = list removeAtOrNull index
            // THEN
            element.assertNull()
        }
    }

    @Nested
    inner class Set {
        // ---------- Int ----------

        @Test
        fun `should replace the head with an index as an int that equals 0`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a")
            val index = 0
            val element = "b"
            // WHEN
            assertPass { list[index] = element }
            // THEN
            element.run {
                assertEquals(list[index])
                assertEquals(list.head)
            }
        }

        @Test
        fun `should replace a tail's element with an index as an int in 1 until the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a", "b")
            val index = 1
            val element = "c"
            // WHEN
            assertPass { list[index] = element }
            // THEN
            list[index] assertEquals element
        }

        @Test
        fun `should throw an error with an index as an int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("one")
            val index: Int = list.size
            val element = "error"
            // WHEN
            val error: IndexOutOfBoundsException =
                assertFailsWith { list[index] = element }
            // THEN
            error.message.assertNotNull() assertEquals indexOutOfBoundsMessage(
                index,
                list.size
            )
        }

        // ---------- PositiveInt ----------

        @Test
        fun `should replace the head with an index as a positive int that equals 0`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a")
            val index = PositiveInt(0)
            val element = "b"
            // WHEN
            assertPass { list[index] = element }
            // THEN
            element.run {
                assertEquals(list[index])
                assertEquals(list.head)
            }
        }

        @Test
        fun `should replace a tail's element with an index as a positive int in 1 until the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a", "b")
            val index = PositiveInt(1)
            val element = "c"
            // WHEN
            assertPass { list[index] = element }
            // THEN
            list[index] assertEquals element
        }

        @Test
        fun `should throw an error with an index as a positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a")
            val index = PositiveInt(list.size)
            val element = "b"
            // WHEN
            val error: IndexOutOfBoundsException =
                assertFailsWith { list[index] = element }
            // THEN
            error.message.assertNotNull() assertEquals indexOutOfBoundsMessage(
                index.value,
                list.size
            )
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should replace a tail's element with an index as a strictly positive int in 1 until the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a", "b")
            val index = StrictlyPositiveInt(1)
            val element = "c"
            // WHEN
            assertPass { list[index] = element }
            // THEN
            list[index] assertEquals element
        }

        @Test
        fun `should throw an error with an index as a strictly positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a")
            val index: StrictlyPositiveInt = list.typedSize
            val element = "b"
            // WHEN
            val error: IndexOutOfBoundsException =
                assertFailsWith { list[index] = element }
            // THEN
            error.message.assertNotNull() assertEquals indexOutOfBoundsMessage(
                index.value,
                list.size
            )
        }
    }

    @Nested
    inner class SetOrNull {
        // ---------- Int ----------

        @Test
        fun `should replace the head with an index as an int that equals 0`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a")
            val index = 0
            val element = "b"
            // WHEN
            val result: String? = list.setOrNull(index, element)
            // THEN
            result.assertNotNull().run {
                assertEquals(element)
                assertEquals(list[index])
                assertEquals(list.head)
            }
        }

        @Test
        fun `should replace a tail's element with an index as an int in 1 until the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a", "b")
            val index = 1
            val element = "c"
            // WHEN
            val result: String? = list.setOrNull(index, element)
            // THEN
            result.assertNotNull().run {
                assertEquals(element)
                assertEquals(list[index])
            }
        }

        @Test
        fun `should return null with an index as an int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a")
            val index: Int = list.size
            val element = "b"
            // WHEN
            val result: String? = list.setOrNull(index, element)
            // THEN
            result.assertNull()
        }

        // ---------- PositiveInt ----------

        @Test
        fun `should replace the head with an index as a positive int that equals 0`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a")
            val index = PositiveInt(0)
            val element = "b"
            // WHEN
            val result: String? = list.setOrNull(index, element)
            // THEN
            result.assertNotNull().run {
                assertEquals(element)
                assertEquals(list[index])
                assertEquals(list.head)
            }
        }

        @Test
        fun `should replace a tail's element with an index as a positive int in 1 until the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a", "b")
            val index = PositiveInt(1)
            val element = "c"
            // WHEN
            val result: String? = list.setOrNull(index, element)
            // THEN
            result.assertNotNull().run {
                assertEquals(element)
                assertEquals(list[index])
            }
        }

        @Test
        fun `should return null with an index as a positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a")
            val index = PositiveInt(list.size)
            val element = "b"
            // WHEN
            val result: String? = list.setOrNull(index, element)
            // THEN
            result.assertNull()
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should replace a tail's element with an index as a strictly positive int in 1 until the list's size`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a", "b")
            val index = StrictlyPositiveInt(1)
            val element = "c"
            // WHEN
            val result: String? = list.setOrNull(index, element)
            // THEN
            result.assertNotNull().run {
                assertEquals(element)
                assertEquals(list[index])
            }
        }

        @Test
        fun `should return null with an index as a strictly positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableListJvm<String> =
                NotEmptyMutableListJvm("a")
            val index: StrictlyPositiveInt = list.typedSize
            val element = "b"
            // WHEN
            val result: String? = list.setOrNull(index, element)
            // THEN
            result.assertNull()
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class ArrayToNotEmptyMutableList {
        @Test
        fun `should pass with an array containing at least 1 element`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            // WHEN
            val list: NotEmptyMutableListJvm<Int> =
                assertPass(array::toNotEmptyMutableListJvm)
            // THEN
            list.run {
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
                block = array::toNotEmptyMutableListJvm
            )
        }
    }

    @Nested
    inner class CollectionToNotEmptyMutableList {
        @Test
        fun `should pass with a collection containing at least 1 element`() {
            // GIVEN
            val collection: Collection<Int> = listOf(1, 2, 3)
            // WHEN
            val list: NotEmptyMutableListJvm<Int> =
                assertPass(collection::toNotEmptyMutableListJvm)
            // THEN
            list.run {
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
            assertFailsWith<IllegalArgumentException>(
                block = collection::toNotEmptyMutableListJvm
            )
        }
    }

    @Nested
    inner class ArrayToNotEmptyMutableListOrNull {
        @Test
        fun `should pass with an array containing at least 1 element`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            // WHEN
            val list: NotEmptyMutableListJvm<Int>? =
                array.toNotEmptyMutableListJvmOrNull()
            // THEN
            list.assertNotNull().run {
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
            val list: NotEmptyMutableListJvm<Int>? =
                array.toNotEmptyMutableListJvmOrNull()
            // THEN
            list.assertNull()
        }
    }

    @Nested
    inner class CollectionToNotEmptyMutableListOrNull {
        @Test
        fun `should pass with a collection containing at least 1 element`() {
            // GIVEN
            val collection: Collection<Int> = listOf(1, 2, 3)
            // WHEN
            val list: NotEmptyMutableListJvm<Int>? =
                collection.toNotEmptyMutableListJvmOrNull()
            // THEN
            list.assertNotNull().run {
                size assertEquals collection.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals collection.elementAt(index)
                }
            }
        }

        @Test
        fun `should return null with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptyList()
            // WHEN
            val list: NotEmptyMutableListJvm<Int>? =
                collection.toNotEmptyMutableListJvmOrNull()
            // THEN
            list.assertNull()
        }
    }

    @Nested
    inner class ArrayToNotEmptyMutableListOrElse {
        @Test
        fun `should pass with an array containing at least 1 element`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            val defaultValue: NotEmptyMutableListJvm<Int> = array.map { -it }
                .toNotEmptyMutableListJvm()
            // WHEN
            val list: NotEmptyMutableListJvm<Int> =
                array toNotEmptyMutableListJvmOrElse { defaultValue }
            // THEN
            list.run {
                size assertEquals array.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals array.elementAt(index)
                    element assertNotEquals defaultValue[index]
                }
            }
        }

        @Test
        fun `should return the default value with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            val defaultValue: NotEmptyMutableListJvm<Int> =
                NotEmptyMutableListJvm(-1, -2, -3)
            // WHEN
            val list: NotEmptyMutableListJvm<Int> =
                array toNotEmptyMutableListJvmOrElse { defaultValue }
            // THEN
            list.run {
                size assertEquals defaultValue.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals defaultValue[index]
                }
            }
        }
    }

    @Nested
    inner class CollectionToNotEmptyMutableListOrElse {
        @Test
        fun `should pass with a collection containing at least 1 element`() {
            // GIVEN
            val collection: Collection<Int> = listOf(1, 2, 3)
            val defaultValue: NotEmptyMutableListJvm<Int> =
                collection.map { -it }
                    .toNotEmptyMutableListJvm()
            // WHEN
            val list: NotEmptyMutableListJvm<Int> =
                collection toNotEmptyMutableListJvmOrElse { defaultValue }
            // THEN
            list.run {
                size assertEquals collection.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals collection.elementAt(index)
                    element assertNotEquals defaultValue[index]
                }
            }
        }

        @Test
        fun `should return the default value with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptyList()
            val defaultValue: NotEmptyMutableListJvm<Int> =
                NotEmptyMutableListJvm(-1, -2, -3)
            // WHEN
            val list: NotEmptyMutableListJvm<Int> =
                collection toNotEmptyMutableListJvmOrElse { defaultValue }
            // THEN
            list.run {
                size assertEquals defaultValue.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals defaultValue[index]
                }
            }
        }
    }
}
