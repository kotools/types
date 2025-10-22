package org.kotools.types.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class IntegerJavaSample {
    @Test
    void constructorLong() {
        final String result = Integers.from(Long.MAX_VALUE)
                .toString();
        final String expected = "9223372036854775807";
        Assertions.assertEquals(expected, result);
    }

    @Test
    void constructorString() {
        final String integer = Integers.from(Long.MAX_VALUE)
                .toString();
        Assertions.assertEquals("9223372036854775807", integer);

        final String plusSignedInteger = Integers.parse("+" + Long.MAX_VALUE)
                .toString();
        Assertions.assertEquals("9223372036854775807", plusSignedInteger);

        final String minusSignedInteger = Integers.from(Long.MIN_VALUE)
                .toString();
        Assertions.assertEquals("-9223372036854775808", minusSignedInteger);
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
}
