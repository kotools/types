package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class IntegerJavaSample {
    @Test
    void constructorLong() {
        // Given
        final long number = 9223372036854775807L;
        // When
        final Integer integer = new Integer(number);
        // Then
        final String result = integer.toString();
        final String expected = String.valueOf(number);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void constructorString() {
        // Given
        final String text = "9223372036854775807";
        // When
        final Integer integer = new Integer(text);
        // Then
        final String result = integer.toString();
        Assertions.assertEquals(text, result);
    }

    @Test
    void equalsOverride() {
        // Given
        final Integer x = new Integer(9223372036854775807L);
        final Integer y = new Integer(9223372036854775807L);
        // When
        final boolean equality = x.equals(y);
        // Then
        Assertions.assertTrue(equality);
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
