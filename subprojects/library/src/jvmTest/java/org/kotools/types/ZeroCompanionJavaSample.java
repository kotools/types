package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
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
}
