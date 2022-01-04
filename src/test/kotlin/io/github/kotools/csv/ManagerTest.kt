package io.github.kotools.csv

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ManagerTest {
    @Test
    fun `isValid should return true`(): Unit = assertTrue {
        object : ManagerImpl() {}
            .apply { file = "test" }
            .isValid()
    }

    @Test
    fun `isValid should return false`(): Unit = assertFalse {
        object : ManagerImpl() {}
            .isValid()
    }
}
