package org.kotools.types.number;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
public class NegativeIntegerJavaSample {
    // --------------------------- Factory functions ---------------------------

    @Test
    void fromLong() {
        final NegativeInteger result = NegativeInteger.fromLong(-42L);
        final boolean check = result.toInteger().equals(Integer.fromLong(-42));
        if (!check) throw new IllegalStateException("Check failed.");

        try {
            NegativeInteger.fromLong(0L);
            throw new IllegalStateException("Check failed.");
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    void fromInteger() {
        final Integer value = Integer.fromLong(-42);
        final NegativeInteger result = NegativeInteger.fromInteger(value);
        final boolean check = result.toInteger().equals(value);
        if (!check) throw new IllegalStateException("Check failed.");

        final Integer zero = Integer.fromLong(0);
        try {
            NegativeInteger.fromInteger(zero);
            throw new IllegalStateException("Check failed.");
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    void parsing() {
        final NegativeInteger result = NegativeInteger.parse("-00042");
        final boolean check = result.toInteger().equals(Integer.fromLong(-42));
        if (!check) throw new IllegalStateException("Check failed.");

        try {
            NegativeInteger.parse("3.14");
            throw new IllegalStateException("Check failed.");
        } catch (NumberFormatException ignored) {
        }

        try {
            NegativeInteger.parse("0");
            throw new IllegalStateException("Check failed.");
        } catch (IllegalArgumentException ignored) {
        }
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    void structuralEquality() {
        final NegativeInteger x = NegativeInteger.fromLong(-42);
        final NegativeInteger y = NegativeInteger.fromInteger(x.toInteger());
        final boolean equality = x.equals(y) && x.hashCode() == y.hashCode();
        if (!equality) throw new IllegalStateException("Check failed.");

        final NegativeInteger z = NegativeInteger.fromLong(-7);
        final boolean inequality = !x.equals(z) && x.hashCode() != z.hashCode();
        if (!inequality) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toNonPositiveInteger() {
        final long number = -42L;
        final NegativeInteger x = NegativeInteger.fromLong(number);

        final NonPositiveInteger result = x.toNonPositiveInteger();

        final NonPositiveInteger expected = NonPositiveInteger.fromLong(number);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void toNonZeroInteger() {
        final long number = -42L;
        final NegativeInteger x = NegativeInteger.fromLong(number);

        final NonZeroInteger result = x.toNonZeroInteger();

        final NonZeroInteger expected = NonZeroInteger.fromLong(number);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void toInteger() {
        final int number = -42;
        final NegativeInteger x = NegativeInteger.fromLong(number);

        final Integer result = x.toInteger();

        final Integer expected = Integer.fromLong(number);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void toStringOverride() {
        final int number = -42;
        final NegativeInteger x = NegativeInteger.fromLong(number);

        final String result = x.toString();

        final String expected = String.valueOf(number);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }
}
