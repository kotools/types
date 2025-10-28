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

    @Test
    void constructorWithDecimalString() {
        // Given
        final String text = "18446744073709551614";
        // When
        final Integer integer = new Integer(text);
        // Then
        final String result = integer.toString();
        Assertions.assertEquals(text, result);
    }

    // -------------------- Structural equality operations ---------------------

    @Test
    void equalsWithIntegerHavingSameValue() {
        // Given
        final Integer integer = new Integer(Long.MAX_VALUE);
        final Integer other = new Integer(Long.MAX_VALUE);
        // When
        final boolean result = integer.equals(other);
        // Then
        Assertions.assertTrue(result);
    }

    @Test
    void hashCodeOverride() {
        // Given
        final Integer integer = new Integer(9223372036854775807L);
        // When
        final int hashCode = integer.hashCode();
        // Then
        final int expected = new Integer(9223372036854775807L)
                .hashCode();
        Assertions.assertEquals(expected, hashCode);
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    void plus() {
        // Given
        final Integer x = new Integer(9223372036854775807L);
        final Integer y = new Integer(9223372036854775807L);
        // When
        final Integer sum = x.plus(y);
        // Then
        final Integer expected = new Integer("18446744073709551614");
        Assertions.assertEquals(expected, sum);
    }

    @Test
    void minus() {
        // Given
        final Integer x = new Integer(-9223372036854775807L);
        final Integer y = new Integer(9223372036854775807L);
        // When
        final Integer difference = x.minus(y);
        // Then
        final Integer expected = new Integer("-18446744073709551614");
        Assertions.assertEquals(expected, difference);
    }

    @Test
    void times() {
        // Given
        final Integer x = new Integer(9223372036854775807L);
        final Integer y = new Integer(10);
        // When
        final Integer product = x.times(y);
        // Then
        final Integer expected = new Integer("92233720368547758070");
        Assertions.assertEquals(expected, product);
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toStringOverride() {
        // Given
        final Integer integer = new Integer(9223372036854775807L);
        // When
        final String integerString = integer.toString();
        // Then
        final String expected = String.valueOf(9223372036854775807L);
        Assertions.assertEquals(expected, integerString);
    }
}
