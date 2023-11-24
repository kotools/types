package kotools.types.internal

import kotlin.test.Test
import kotlin.test.assertEquals

class KotoolsTypesVersionTest {
    @Test
    fun toString_should_pass() {
        KotoolsTypesVersion.values().forEach {
            val actual = "$it"
            val expected: String = it.name.drop(1)
                .replace('_', '.')
            assertEquals(expected, actual)
        }
    }
}
