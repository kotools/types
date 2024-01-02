package kotools.types.internal

import kotlin.test.Test
import kotlin.test.assertEquals

class KotoolsTypesPackageTest {
    @Test
    fun toString_should_pass_on_each_values(): Unit = KotoolsTypesPackage
        .values()
        .asSequence()
        .map { "kotools.types.${it.name.lowercase()}" to "$it" }
        .forEach { assertEquals(expected = it.first, actual = it.second) }
}
