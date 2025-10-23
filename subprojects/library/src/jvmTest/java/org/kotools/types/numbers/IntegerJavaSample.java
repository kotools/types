package org.kotools.types.numbers;

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
        final Integer integer = Integers.from(number);
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
        final Integer integer = Integers.parse(text);
        // Then
        final String result = integer.toString();
        Assertions.assertEquals(text, result);
    }

    @Test
    void plus() {
        // Given
        final Integer x = Integers.from(9223372036854775807L);
        final Integer y = Integers.from(9223372036854775807L);
        // When
        final Integer sum = x.plus(y);
        // Then
        final Integer expected = Integers.parse("18446744073709551614");
        Assertions.assertEquals(expected, sum);
    }

    @Test
    void minus() {
        // Given
        final Integer x = Integers.from(-9223372036854775807L);
        final Integer y = Integers.from(9223372036854775807L);
        // When
        final Integer difference = x.minus(y);
        // Then
        final Integer expected = Integers.parse("-18446744073709551614");
        Assertions.assertEquals(expected, difference);
    }

    @Test
    void times() {
        // Given
        final Integer x = Integers.from(9223372036854775807L);
        final Integer y = Integers.from(10);
        // When
        final Integer product = x.times(y);
        // Then
        final Integer expected = Integers.parse("92233720368547758070");
        Assertions.assertEquals(expected, product);
    }
}
