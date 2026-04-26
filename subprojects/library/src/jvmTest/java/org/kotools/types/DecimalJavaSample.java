package org.kotools.types;

import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class DecimalJavaSample {
    // ------------------------------- Creations -------------------------------

    @Test
    void ofLong() {
        // Given
        final long number = 123456789L;
        // When
        final Decimal result = Decimal.of(number);
        // Then
        final String resultAsString = result.toString();
        final String expected = String.valueOf(number);
        final boolean check = resultAsString.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void fromDecimalString() {
        // Given
        final String text = "+000.000123000";
        // When
        final Decimal result = Decimal.fromDecimal(text);
        // Then
        final String resultAsString = result.toString();
        final String expected = "0.000123";
        final boolean check = resultAsString.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    void equality() {
        // Given
        final Decimal x = Decimal.of(1);
        final Decimal y = Decimal.fromDecimal("+0001");
        final Decimal z = Decimal.fromDecimal("+1.000");
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
        final Decimal decimal = Decimal.of(number);
        // When
        final String result = decimal.toString();
        // Then
        final String expected = String.valueOf(number);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }
}
