package org.kotools.types.geojson

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class GeoJsonTypeTest {
    // ------------------------------ toString() -------------------------------

    @Test
    fun toStringReturnsEntryName(): Unit = GeoJsonType.entries.forEach {
        val actual: String = it.toString()
        assertEquals(expected = it.name, actual)
    }
}
