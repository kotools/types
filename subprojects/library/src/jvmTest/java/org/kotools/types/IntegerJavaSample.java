package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class IntegerJavaSample {
    // ----------------------------- Constructors ------------------------------

    @Test
    void constructorWithLong() {
        // Given
        final long number = 9_223_372_036_854_775_807L;
        // When
        final Integer integer = new Integer(number);
        // Then
        final String result = integer.toString();
        final String expected = String.valueOf(number);
        Assertions.assertEquals(expected, result);
    }

    // -------------------- Structural equality operations ---------------------

    @Test
    void equalsWithIntegerHavingSameValue() {
        // Given
        final long number = 9_223_372_036_854_775_807L;
        final Integer integer = new Integer(number);
        final Integer other = new Integer(number);
        // When
        final boolean result = integer.equals(other);
        // Then
        Assertions.assertTrue(result);
    }

    @Test
    void hashCodeOverride() {
        // Given
        final long number = 9_223_372_036_854_775_807L;
        final Integer integer = new Integer(number);
        // When
        final int result = integer.hashCode();
        // Then
        final int expected = new Integer(number)
                .hashCode();
        Assertions.assertEquals(expected, result);
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    void plus() {
        // Given
        final long number = 9_223_372_036_854_775_807L;
        final Integer x = new Integer(number);
        final Integer y = new Integer(number);
        // When
        final Integer result = x.plus(y);
        // Then
        final Integer expected = Integer.parse("18446744073709551614");
        Assertions.assertEquals(expected, result);
    }

    @Test
    void minus() {
        // Given
        final Integer x = new Integer(-9_223_372_036_854_775_807L);
        final Integer y = new Integer(9_223_372_036_854_775_807L);
        // When
        final Integer result = x.minus(y);
        // Then
        final Integer expected = Integer.parse("-18446744073709551614");
        Assertions.assertEquals(expected, result);
    }

    @Test
    void times() {
        // Given
        final Integer x = new Integer(9_223_372_036_854_775_807L);
        final Integer y = new Integer(10);
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
        final Integer integer = new Integer(number);
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
