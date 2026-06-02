package org.kotools.types.geojson

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class GeometryTypeTest {
    // ------------------------------ toString() -------------------------------

    @Test
    fun toStringReturnsEntryName(): Unit = GeometryType.entries.forEach {
        val actual: String = it.toString()
        assertEquals(expected = it.name, actual)
    }
}
