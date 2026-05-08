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

    @Test
    void parse() {
        // Given
        final String value = "+000123";
        // When
        final Integer result = Integer.parse(value);
        // Then
        final Integer expected = Integer.of(123);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    void unaryMinus() {
        // Given
        final Integer integer = Integer.of(42);
        // When
        final Integer result = integer.unaryMinus();
        // Then
        final Integer expected = Integer.of(-42);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void plus() {
        // Given
        final Integer x = Integer.of(9223372036854775807L);
        final Integer y = Integer.of(10);
        // When
        final Integer sum = x.plus(y);
        // Then
        final Integer expected = Integer.parse("9223372036854775817");
        final boolean check = sum.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void minus() {
        // Given
        final Integer x = Integer.of(-9223372036854775807L);
        final Integer y = Integer.of(10);
        // When
        final Integer difference = x.minus(y);
        // Then
        final Integer expected = Integer.parse("-9223372036854775817");
        final boolean check = difference.equals(expected);
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

    @Test
    void compareTo() {
        // Given
        final Integer x = Integer.of(Long.MIN_VALUE);
        final Integer y = Integer.of(Long.MAX_VALUE);
        // When & Then
        final boolean check = x.compareTo(y) < 0;
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
