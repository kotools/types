package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

import java.util.Random;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
class PositiveIntegerJavaSample {
    @Test
    void toStringOverride() {
        final int number = new Random()
                .nextInt(1, Integer.MAX_VALUE);
        final String integerAsString = PositiveInteger.orThrow(number)
                .toString();
        final String expected = Integer.valueOf(number)
                .toString();
        Assertions.assertEquals(expected, integerAsString);
    }
}
