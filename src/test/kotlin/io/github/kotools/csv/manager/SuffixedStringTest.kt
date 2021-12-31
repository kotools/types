package io.github.kotools.csv.manager

import io.github.kotools.csv.test.assertEquals
import io.github.kotools.csv.test.assertNotEquals
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class SuffixedStringTest {
    private companion object {
        private const val FILE_SUFFIX: String = ".csv"
        private const val FOLDER_SUFFIX: String = "/"
    }

    @Nested
    inner class CsvFile {
        private var file: String by SuffixedString.csvFile()

        @Test
        fun `should set with non-suffixed value`() {
            val value = "test1"
            file = value
            file assertEquals "$value$FILE_SUFFIX"
        }

        @Test
        fun `should set with suffixed value`() {
            val value = "test2$FILE_SUFFIX"
            file = value
            file assertEquals value
        }

        @Test
        fun `shouldn't set with blank value`() {
            val value = " "
            file = value
            file assertNotEquals value
        }

        @Test
        fun `shouldn't set with same value`() {
            val value = "test3"
            file = value
            val suffixedValue = "$value$FILE_SUFFIX"
            file = suffixedValue
            file assertEquals suffixedValue
        }
    }

    @Nested
    inner class Folder {
        private var folder: String by SuffixedString.folder()

        @Test
        fun `should set with non-suffixed value`() {
            val value = "test1"
            folder = value
            folder assertEquals "$value$FOLDER_SUFFIX"
        }

        @Test
        fun `should set with suffixed value`() {
            val value = "test2$FOLDER_SUFFIX"
            folder = value
            folder assertEquals value
        }

        @Test
        fun `shouldn't set with blank value`() {
            val value = " "
            folder = value
            folder assertNotEquals value
        }

        @Test
        fun `shouldn't set with same value`() {
            val value = "test3"
            folder = value
            val suffixedValue = "$value$FOLDER_SUFFIX"
            folder = suffixedValue
            folder assertEquals suffixedValue
        }
    }
}
