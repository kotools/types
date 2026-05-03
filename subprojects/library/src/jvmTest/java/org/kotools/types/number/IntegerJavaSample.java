package org.kotools.types.number;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
public class IntegerJavaSample {
    // ------------------------------- Creations -------------------------------

    @Test
    void ofLong() {
        // Given
        final long value = Long.MAX_VALUE;
        // When
        final Integer result = Integer.of(value);
        // Then
        final String s = String.valueOf(result);
        final String expected = String.valueOf(value);
        final boolean check = expected.equals(s);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    void structuralEquality() {
        // Given
        final long value = Long.MAX_VALUE;
        final Integer x = Integer.of(value);
        final Integer y = Integer.of(value);
        // When
        final boolean equality = x.equals(y);
        final boolean hashConformity = x.hashCode() == y.hashCode();
        // Then
        final boolean check = equality && hashConformity;
        if (!check) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toStringOverride() {
        // Given
        final Integer integer = Integer.of(Long.MAX_VALUE);
        // When
        final String result = integer.toString();
        // Then
        final boolean check = result.equals("9223372036854775807");
        if (!check) throw new IllegalStateException("Check failed.");
    }
}
