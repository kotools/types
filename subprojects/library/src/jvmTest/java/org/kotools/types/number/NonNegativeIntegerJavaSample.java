package org.kotools.types.number;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
public class NonNegativeIntegerJavaSample {
    // --------------------------- Factory functions ---------------------------

    @Test
    void fromInteger() {
        final Integer value = Integer.fromLong(42);
        final NonNegativeInteger result =
                NonNegativeInteger.fromInteger(value);
        final boolean check = String.valueOf(result).equals("42");
        if (!check) throw new IllegalStateException("Check failed.");

        final Integer negative = Integer.fromLong(-1);
        try {
            NonNegativeInteger.fromInteger(negative);
            throw new IllegalStateException("Check failed.");
        } catch (IllegalArgumentException ignored) {
        }
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    void structuralEquality() {
        final Integer value = Integer.fromLong(42);
        final NonNegativeInteger x = NonNegativeInteger.fromInteger(value);
        final NonNegativeInteger y = NonNegativeInteger.fromInteger(value);
        final boolean equality = x.equals(y) && x.hashCode() == y.hashCode();
        if (!equality) throw new IllegalStateException("Check failed.");

        final Integer other = Integer.fromLong(7);
        final NonNegativeInteger z = NonNegativeInteger.fromInteger(other);
        final boolean inequality =
                !x.equals(z) && x.hashCode() != z.hashCode();
        if (!inequality) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toInteger() {
        final Integer value = Integer.fromLong(42);
        final NonNegativeInteger nonNegativeInteger =
                NonNegativeInteger.fromInteger(value);
        final Integer result = nonNegativeInteger.toInteger();
        final boolean check = result.equals(value);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void toStringOverride() {
        final Integer value = Integer.fromLong(42);
        final NonNegativeInteger nonNegativeInteger =
                NonNegativeInteger.fromInteger(value);
        final String result = String.valueOf(nonNegativeInteger);
        final boolean check = result.equals("42");
        if (!check) throw new IllegalStateException("Check failed.");
    }
}
