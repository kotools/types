package org.kotools.types.geojson

import org.kotools.types.ExperimentalKotoolsTypesApi

// TODO: Add @since version in GeoJsonType's documentation.

/**
 * Enumerates GeoJSON types specified in
 * [RFC 7946 section 1.4](https://www.rfc-editor.org/info/rfc7946/#section-1.4).
 */
@ExperimentalKotoolsTypesApi
public enum class GeoJsonType {
    /** The `Feature` GeoJSON type. */
    Feature,

    /** The `FeatureCollection` GeoJSON type. */
    FeatureCollection,

    /** The `Point` GeoJSON type. */
    Point,

    /** The `MultiPoint` GeoJSON type. */
    MultiPoint,

    /** The `LineString` GeoJSON type. */
    LineString,

    /** The `MultiLineString` GeoJSON type. */
    MultiLineString,

    /** The `Polygon` GeoJSON type. */
    Polygon,

    /** The `MultiPolygon` GeoJSON type. */
    MultiPolygon,

    /** The `GeometryCollection` GeoJSON type. */
    GeometryCollection
}
