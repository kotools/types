package io.github.kotools.csv.manager

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CsvManagerTest {
    @Test
    fun `isValid should return true`(): Unit = assertTrue {
        object : ManagerConfiguration() {}
            .apply { file = "test" }
            .isValid()
    }

    @Test
    fun `isValid should return false`(): Unit = assertFalse {
        object : ManagerConfiguration() {}
            .isValid()
    }
}
