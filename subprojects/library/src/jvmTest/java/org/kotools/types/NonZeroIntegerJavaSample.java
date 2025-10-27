package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class NonZeroIntegerJavaSample {
    @Test
    void constructorWithNonZeroInt() {
        // Given
        final int number = 2_147_483_647;
        // When
        final NonZeroInteger result = new NonZeroInteger(number);
        // Then
        final String resultAsString = result.toString();
        final String expected = String.valueOf(number);
        Assertions.assertEquals(expected, resultAsString);
    }

    @Test
    void toInteger() {
        // Given
        final int number = 2_147_483_647;
        final NonZeroInteger x = new NonZeroInteger(number);
        // When
        final Integer result = x.toInteger();
        // Then
        final Integer expected = new Integer(number);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void toStringOverride() {
        // Given
        final int number = 2_147_483_647;
        final NonZeroInteger integer = new NonZeroInteger(number);
        // When
        final String result = integer.toString();
        // Then
        final String expected = String.valueOf(number);
        Assertions.assertEquals(expected, result);
    }
}
