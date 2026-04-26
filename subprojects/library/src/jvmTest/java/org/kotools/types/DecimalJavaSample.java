package org.kotools.types;

import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class DecimalJavaSample {
    // ------------------------------ Comparisons ------------------------------

    @Test
    void equality() {
        // Given
        final Decimal x = Decimal.Companion.of(1);
        final Decimal y = Decimal.Companion.fromString("+0001");
        final Decimal z = Decimal.Companion.fromString("+1.000");
        // When
        final boolean equality = x.equals(y) && y.equals(z);
        final boolean hashConformity = x.hashCode() == y.hashCode()
                && y.hashCode() == z.hashCode();
        // Then
        final boolean check = equality && hashConformity;
        if (!check) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toStringOverride() {
        // Given
        final long number = 123456789L;
        final Decimal decimal = Decimal.Companion.of(number);
        // When
        final String result = decimal.toString();
        // Then
        final String expected = String.valueOf(number);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }
}
