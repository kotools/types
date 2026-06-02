package org.kotools.types.geojson

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class GeometryTypeSample {
    @Test
    fun toGeoJsonType() {
        val geometryType: GeometryType = GeometryType.Point
        val result: GeoJsonType = geometryType.toGeoJsonType()
        check(result == GeoJsonType.Point)
    }
}
