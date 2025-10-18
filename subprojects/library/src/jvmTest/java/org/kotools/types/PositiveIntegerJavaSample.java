package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class PositiveIntegerJavaSample {
    @Test
    void intToPositiveInteger() {
        final int number = 123456;
        final String result = PositiveIntegers.fromInteger(number)
                .toString();
        final String expected = String.valueOf(number);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void longToPositiveInteger() {
        final long number = 123456;
        final String result = PositiveIntegers.fromInteger(number)
                .toString();
        final String expected = String.valueOf(number);
        Assertions.assertEquals(expected, result);
    }
}
