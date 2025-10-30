package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class IntegerJavaSample {
    // -------------------- Structural equality operations ---------------------

    @Test
    void equalsOverride() {
        final long number = Long.MAX_VALUE;
        final Integer x = Integer.from(number);
        final Integer y = Integer.from(number);
        final boolean result = x.equals(y);
        if (!result) throw new IllegalStateException("Check failed.");
    }

    @Test
    void hashCodeOverride() {
        final long number = Long.MAX_VALUE;
        final Integer x = Integer.from(number);
        final Integer y = Integer.from(number);
        final boolean result = x.hashCode() == y.hashCode();
        if (!result) throw new IllegalStateException("Check failed.");
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    void plus() {
        // Given
        final long number = 9_223_372_036_854_775_807L;
        final Integer x = Integer.from(number);
        final Integer y = Integer.from(number);
        // When
        final Integer result = x.plus(y);
        // Then
        final Integer expected = Integer.parse("18446744073709551614");
        Assertions.assertEquals(expected, result);
    }

    @Test
    void minus() {
        // Given
        final Integer x = Integer.from(-9_223_372_036_854_775_807L);
        final Integer y = Integer.from(9_223_372_036_854_775_807L);
        // When
        final Integer result = x.minus(y);
        // Then
        final Integer expected = Integer.parse("-18446744073709551614");
        Assertions.assertEquals(expected, result);
    }

    @Test
    void times() {
        // Given
        final Integer x = Integer.from(9_223_372_036_854_775_807L);
        final Integer y = Integer.from(10);
        // When
        final Integer result = x.times(y);
        // Then
        final Integer expected = Integer.parse("92233720368547758070");
        Assertions.assertEquals(expected, result);
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toStringOverride() {
        // Given
        final long number = 9_223_372_036_854_775_807L;
        final Integer integer = Integer.from(number);
        // When
        final String result = integer.toString();
        // Then
        final String expected = String.valueOf(number);
        Assertions.assertEquals(expected, result);
    }

    // ----------------------- Class-level declarations ------------------------

    @Test
    void from() {
        // Given
        final long number = 9_223_372_036_854_775_807L;
        // When
        final Integer integer = Integer.from(number);
        // Then
        final String result = integer.toString();
        final String expected = String.valueOf(number);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void parseWithDecimalString() {
        // Given
        final String text = "1234";
        // When
        final Integer integer = Integer.parse(text);
        // Then
        final String result = integer.toString();
        Assertions.assertEquals(text, result);
    }
}
