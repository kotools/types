package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class PositiveIntegerJavaSample {
    @Test
    void intToPositiveInteger() {
        final String result = PositiveIntegers.fromInteger(Integer.MAX_VALUE)
                .toString();
        final String expected = "2147483647";
        Assertions.assertEquals(expected, result);
    }

    @Test
    void longToPositiveInteger() {
        final String result = PositiveIntegers.fromInteger(Long.MAX_VALUE)
                .toString();
        final String expected = "9223372036854775807";
        Assertions.assertEquals(expected, result);
    }
}
