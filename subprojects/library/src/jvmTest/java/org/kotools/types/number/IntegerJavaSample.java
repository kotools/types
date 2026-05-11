package org.kotools.types.number;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
public class IntegerJavaSample {
    // ------------------------------- Creations -------------------------------

    @Test
    void of() {
        final long value = 9_223_372_036_854_775_807L;

        final Integer result = Integer.of(value);

        final String resultString = String.valueOf(result);
        final String expected = String.valueOf(value);
        final boolean check = resultString.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toStringOverride() {
        final long value = 9_223_372_036_854_775_807L;
        final Integer integer = Integer.of(value);

        final String result = integer.toString();

        final String expected = String.valueOf(value);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }
}
