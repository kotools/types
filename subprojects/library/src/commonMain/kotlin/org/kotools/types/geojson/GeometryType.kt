package org.kotools.types.geojson

import org.kotools.types.ExperimentalKotoolsTypesApi

// TODO: Add @since version in GeometryType's documentation.

/**
 * Enumerates geometry types specified in
 * [RFC 7946 section 1.4](https://www.rfc-editor.org/info/rfc7946/#section-1.4).
 */
@ExperimentalKotoolsTypesApi
public enum class GeometryType {
    /** The `Point` geometry type. */
    Point,

    /** The `MultiPoint` geometry type. */
    MultiPoint,

    /** The `LineString` geometry type. */
    LineString,

    /** The `MultiLineString` geometry type. */
    MultiLineString,

    /** The `Polygon` geometry type. */
    Polygon,

    /** The `MultiPolygon` geometry type. */
    MultiPolygon,

    /** The `GeometryCollection` geometry type. */
    GeometryCollection;

    /**
     * Converts this geometry type to a [GeoJsonType].
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: org.kotools.types.geojson.GeometryTypeSample.toGeoJsonType
     * </details>
     */
    public fun toGeoJsonType(): GeoJsonType = when (this) {
        Point -> GeoJsonType.Point
        MultiPoint -> GeoJsonType.MultiPoint
        LineString -> GeoJsonType.LineString
        MultiLineString -> GeoJsonType.MultiLineString
        Polygon -> GeoJsonType.Polygon
        MultiPolygon -> GeoJsonType.MultiPolygon
        GeometryCollection -> GeoJsonType.GeometryCollection
    }
}
