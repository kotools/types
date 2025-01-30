package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@SuppressWarnings("NewClassNamingConvention")
class ZeroCompanionJavaSample {
    @Test
    void orThrow() {
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
