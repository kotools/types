package org.kotools.types;

import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

import java.util.Random;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
class PositiveIntegerCompanionJavaSample {
    @Test
    void orThrowInt() {
        final int number = new Random()
                .nextInt(1, Integer.MAX_VALUE);
        PositiveInteger.orThrow(number);
    }

    @Test
    void orThrowLong() {
        final long number = new Random()
                .nextLong(1, Long.MAX_VALUE);
        PositiveInteger.orThrow(number);
    }
}
