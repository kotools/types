package org.kotools.types.number;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
public class IntegerJavaSample {
    // ------------------------------- Creations -------------------------------

    @Test
    void of() {
        boolean check = String.valueOf(Integer.of(0)).equals("0");
        check = check && String.valueOf(Integer.of(42)).equals("42");
        check = check && String.valueOf(Integer.of(-42)).equals("-42");
        check = check && String.valueOf(Integer.of(Long.MAX_VALUE))
                .equals("9223372036854775807");
        check = check && String.valueOf(Integer.of(Long.MIN_VALUE))
                .equals("-9223372036854775808");
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void parse() {
        final String value = "+000123";

        final Integer result = Integer.parse(value);

        final Integer expected = Integer.of(123);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    void structuralEquality() {
        final Integer x = Integer.of(-123);
        final Integer y = Integer.parse("-000123");

        final boolean check = x.equals(y) && x.hashCode() == y.hashCode();
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void equalsOverride() {
        // Given
        final long number = Long.MAX_VALUE;
        final Integer x = Integer.of(number);
        final Integer y = Integer.of(number);
        // When
        final boolean result = x.equals(y);
        // Then
        if (!result) throw new IllegalStateException("Check failed.");
    }

    @Test
    void hashCodeOverride() {
        // Given
        final long number = Long.MAX_VALUE;
        final Integer integer = Integer.of(number);
        // When
        final int result = integer.hashCode();
        // Then
        final int expected = Integer.of(number)
                .hashCode();
        final boolean check = result == expected;
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void compareTo() {
        // Given
        final Integer x = Integer.of(Long.MIN_VALUE);
        final Integer y = Integer.of(Long.MAX_VALUE);
        // When
        final boolean result = x.compareTo(y) < 0;
        // Then
        if (!result) throw new IllegalStateException("Check failed.");
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    void unaryMinus() {
        // Given
        final Integer x = Integer.of(9223372036854775807L);
        // When
        final Integer result = x.unaryMinus();
        // Then
        final Integer expected = Integer.of(-9223372036854775807L);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void plus() {
        // Given
        final Integer x = Integer.of(9223372036854775807L);
        final Integer y = Integer.of(2);
        // When
        final Integer result = x.plus(y);
        // Then
        final Integer expected = Integer.parse("9223372036854775809");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void minus() {
        // Given
        final Integer x = Integer.of(-9223372036854775807L);
        final Integer y = Integer.of(2);
        // When
        final Integer result = x.minus(y);
        // Then
        final Integer expected = Integer.parse("-9223372036854775809");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void times() {
        // Given
        final Integer x = Integer.of(9223372036854775807L);
        final Integer y = Integer.of(10);
        // When
        final Integer result = x.times(y);
        // Then
        final Integer expected = Integer.parse("92233720368547758070");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void div() {
        // Given
        final Integer x = Integer.parse("922337203685477580700");
        final Integer y = Integer.of(10);
        // When
        final Integer result = x.div(y);
        // Then
        final Integer expected = Integer.parse("92233720368547758070");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void rem() {
        // Given
        final Integer x = Integer.of(42);
        final Integer y = Integer.of(5);
        // When
        final Integer result = x.rem(y);
        // Then
        final Integer expected = Integer.of(2);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toStringOverride() {
        // Given
        final long number = 9223372036854775807L;
        final Integer integer = Integer.of(number);
        // When
        final String result = integer.toString();
        // Then
        final String expected = String.valueOf(number);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }
}
