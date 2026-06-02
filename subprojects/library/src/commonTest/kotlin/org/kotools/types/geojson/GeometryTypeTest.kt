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

    // ---------------------------- toGeoJsonType() ----------------------------

    @Test
    fun toGeoJsonTypeReturnsCorrespondingGeoJsonType(): Unit =
        GeometryType.entries.forEach { geometryType: GeometryType ->
            val actual: GeoJsonType = geometryType.toGeoJsonType()
            val expected: GeoJsonType =
                GeoJsonType.entries.first { it.name == geometryType.name }
            assertEquals(expected, actual)
        }
}
