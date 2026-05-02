package org.kotools.types.number;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
public class DecimalJavaSample {
    // ------------------------------- Creations -------------------------------

    @Test
    void ofLong() {
        // Given
        final long value = 42;
        // When
        final Decimal result = Decimal.of(value);
        // Then
        final String s = String.valueOf(result);
        final String expected = String.valueOf(value);
        final boolean check = expected.equals(s);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void parse() {
        // Given
        final String value = "+042.0";
        // When
        final Decimal result = Decimal.parse(value);
        // Then
        final Decimal expected = Decimal.of(42);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }
}
