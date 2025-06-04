package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

import java.util.Random;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
class NegativeIntegerJavaSample {
    @Test
    void toStringOverride() {
        final int number = new Random()
                .nextInt(Integer.MIN_VALUE, 0);
        final String expected = String.valueOf(number);
        final String actual = NegativeInteger.orThrow(number)
                .toString();
        Assertions.assertEquals(expected, actual);
    }

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

    @Test
    void orThrowString() {
        final long number = new Random()
                .nextLong(Long.MIN_VALUE, 0);
        final String text = Long.toString(number);
        NegativeInteger.orThrow(text);
    }
}
