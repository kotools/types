package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class ZeroCompanionJavaSample {
    @Test
    void fromByte() {
        final byte number = 0;
        boolean isSuccess;
        try {
            Zero.fromByte(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }
}