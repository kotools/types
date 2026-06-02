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
    GeometryCollection
}
