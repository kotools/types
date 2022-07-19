package kotools.types.collections

import io.github.kotools.assert.assertEquals
import kotools.types.number.StrictlyPositiveInt
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertFalse

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
}
