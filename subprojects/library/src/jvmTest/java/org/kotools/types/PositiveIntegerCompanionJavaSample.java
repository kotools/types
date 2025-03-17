package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

@SuppressWarnings("NewClassNamingConvention")
class PositiveIntegerCompanionJavaSample {
    @Test
    void orThrowWithByte() {
        final byte number = (byte) new Random()
                .nextInt(1, Byte.MAX_VALUE);
        boolean isSuccess;
        try {
            PositiveInteger.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }
}
