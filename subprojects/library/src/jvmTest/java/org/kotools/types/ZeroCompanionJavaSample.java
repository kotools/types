package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@SuppressWarnings("NewClassNamingConvention")
class ZeroCompanionJavaSample {
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

    @Test
    void orThrowWithAny() {
        boolean isSuccess;
        try {
            Arrays.asList(0, "000", 0.0, "0.000", "000.0", 000.000)
                    .forEach(Zero::orThrow);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }
}
