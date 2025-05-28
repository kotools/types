package org.kotools.types;

import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

import java.util.Random;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
class NegativeIntegerJavaSample {
    // ------------------------------- Companion -------------------------------

    @Test
    void orThrowInt() {
        final int number = new Random()
                .nextInt(Integer.MIN_VALUE, 0);
        NegativeInteger.orThrow(number);
    }

    @Test
    void orThrowLong() {
        final long number = new Random()
                .nextLong(Long.MIN_VALUE, 0);
        NegativeInteger.orThrow(number);
    }
}
