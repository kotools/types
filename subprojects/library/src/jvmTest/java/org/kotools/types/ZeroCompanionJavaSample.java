package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

import java.util.Arrays;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
class ZeroCompanionJavaSample {
    @Test
    void orThrowWithByte() {
        final byte number = 0;
        boolean isSuccess;
        try {
            Zero.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void orThrowWithShort() {
        final short number = 0;
        boolean isSuccess;
        try {
            Zero.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void orThrowWithInt() {
        final int number = 0;
        boolean isSuccess;
        try {
            Zero.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void orThrowWithLong() {
        final long number = 0;
        boolean isSuccess;
        try {
            Zero.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void orThrowWithFloat() {
        final float number = 0;
        boolean isSuccess;
        try {
            Zero.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void orThrowWithDouble() {
        final double number = 0;
        boolean isSuccess;
        try {
            Zero.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void orThrowWithString() {
        boolean isSuccess;
        try {
            Arrays.asList("0", "000", "0.0", "0.000", "000.0", "000.000")
                    .forEach(Zero::orThrow);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }
}
