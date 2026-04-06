package org.kotools.types;

import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class DecimalJavaSample {
    // ------------------------------- Creations -------------------------------

    @Test
    void fromIntegerLong() {
        // Given
        final long number = 123456789L;
        // When
        final Decimal result = Decimal.fromInteger(number);
        // Then
        final String resultAsString = result.toString();
        final String expected = String.valueOf(number);
        final boolean check = resultAsString.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }
}
