package org.kotools.types.number;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("NewClassNamingConvention")
public class IntegerJavaSample {
    // ------------------------------- Creations -------------------------------

    @Test
    void fromLong() {
        final BiConsumer<Long, String> createsFromLong = (input, expected) -> {
            final Integer result = Integer.fromLong(input);
            final boolean check = String.valueOf(result).equals(expected);
            if (!check) throw new IllegalStateException("Check failed.");
        };

        createsFromLong.accept(0L, "0");
        createsFromLong.accept(42L, "42");
        createsFromLong.accept(-42L, "-42");
        createsFromLong.accept(Long.MAX_VALUE, "9223372036854775807");
        createsFromLong.accept(Long.MIN_VALUE, "-9223372036854775808");
    }

    @Test
    void fromByte() {
        final BiConsumer<Byte, String> createsFromByte = (input, expected) -> {
            final Integer result = Integer.fromByte(input);
            final boolean check = String.valueOf(result).equals(expected);
            if (!check) throw new IllegalStateException("Check failed.");
        };

        createsFromByte.accept((byte) 0, "0");
        createsFromByte.accept((byte) 42, "42");
        createsFromByte.accept((byte) -42, "-42");
        createsFromByte.accept(Byte.MAX_VALUE, "127");
        createsFromByte.accept(Byte.MIN_VALUE, "-128");
    }

    @Test
    void fromShort() {
        final BiConsumer<Short, String> createsFromShort = (input, expected) -> {
            final Integer result = Integer.fromShort(input);
            final boolean check = String.valueOf(result).equals(expected);
            if (!check) throw new IllegalStateException("Check failed.");
        };

        createsFromShort.accept((short) 0, "0");
        createsFromShort.accept((short) 42, "42");
        createsFromShort.accept((short) -42, "-42");
        createsFromShort.accept(Short.MAX_VALUE, "32767");
        createsFromShort.accept(Short.MIN_VALUE, "-32768");
    }

    @Test
    void fromInt() {
        final BiConsumer<java.lang.Integer, String> createsFromInt =
                (input, expected) -> {
                    final Integer result = Integer.fromInt(input);
                    final boolean check =
                            String.valueOf(result).equals(expected);
                    if (!check) throw new IllegalStateException("Check failed.");
                };

        createsFromInt.accept(0, "0");
        createsFromInt.accept(42, "42");
        createsFromInt.accept(-42, "-42");
        createsFromInt.accept(java.lang.Integer.MAX_VALUE, "2147483647");
        createsFromInt.accept(java.lang.Integer.MIN_VALUE, "-2147483648");
    }

    @Test
    void parsing() {
        final BiConsumer<String, String> parsesTo = (input, expected) -> {
            final boolean check = String.valueOf(Integer.parse(input))
                    .equals(expected);
            if (!check) throw new IllegalStateException("Check failed.");
        };

        final Consumer<String> parsingFailsWith = (input) -> {
            try {
                Integer.parse(input);
                throw new IllegalStateException("Check failed.");
            } catch (NumberFormatException ignored) {
            }
        };

        // Parsing normalizes zero:
        parsesTo.accept("0", "0");
        parsesTo.accept("+0", "0");
        parsesTo.accept("-0", "0");
        parsesTo.accept("000", "0");
        parsesTo.accept("+000", "0");
        parsesTo.accept("-000", "0");

        // Parsing removes leading plus sign:
        parsesTo.accept("+42", "42");

        // Parsing removes leading zeros:
        parsesTo.accept("00042", "42");
        parsesTo.accept("-00042", "-42");

        // Parsing preserves canonical representation:
        parsesTo.accept("42", "42");
        parsesTo.accept("-42", "-42");

        // Parsing fails with noninteger string:
        parsingFailsWith.accept("");
        parsingFailsWith.accept("+");
        parsingFailsWith.accept("-");
        parsingFailsWith.accept("12a");
        parsingFailsWith.accept("3.14");
        parsingFailsWith.accept(" 42");
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    void structuralEquality() {
        final BiConsumer<Integer, Object> checkEquality = (integer, other) -> {
            final boolean equality = Objects.equals(integer, other);
            final boolean hashConformity =
                    Objects.hashCode(integer) == Objects.hashCode(other);
            final boolean check = equality && hashConformity;
            if (!check) throw new IllegalStateException("Check failed.");
        };

        final BiConsumer<Integer, Object> checkDiff = (integer, other) -> {
            final boolean equality = !Objects.equals(integer, other);
            final boolean hashConformity =
                    Objects.hashCode(integer) != Objects.hashCode(other);
            final boolean check = equality && hashConformity;
            if (!check) throw new IllegalStateException("Check failed.");
        };

        checkEquality.accept(Integer.fromLong(0), Integer.parse("-000"));
        checkEquality.accept(Integer.fromLong(42), Integer.parse("+00042"));
        checkEquality.accept(Integer.fromLong(-42), Integer.parse("-0042"));

        checkDiff.accept(Integer.fromLong(0), Integer.fromLong(1));
        checkDiff.accept(Integer.fromLong(42), 42);
        checkDiff.accept(Integer.fromLong(-42), null);
    }

    @Test
    void compareTo() {
        final Integer x = Integer.parse("-99999999999999999999");
        final Integer y = Integer.parse("99999999999999999999");
        final boolean result = x.compareTo(y) < 0;
        if (!result) throw new IllegalStateException("Check failed.");
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    void unaryMinus() {
        final Integer x = Integer.parse("99999999999999999999");
        final Integer result = x.unaryMinus();
        final Integer expected = Integer.parse("-99999999999999999999");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void plus() {
        final Integer x = Integer.parse("99999999999999999999");
        final Integer y = Integer.parse("1");
        final Integer result = x.plus(y);
        final Integer expected = Integer.parse("100000000000000000000");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void minus() {
        final Integer x = Integer.parse("-99999999999999999999");
        final Integer y = Integer.parse("1");
        final Integer result = x.minus(y);
        final Integer expected = Integer.parse("-100000000000000000000");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void times() {
        final Integer x = Integer.parse("99999999999999999999");
        final Integer y = Integer.parse("10");
        final Integer result = x.times(y);
        final Integer expected = Integer.parse("999999999999999999990");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void euclideanDivision() {
        final Integer x = Integer.fromLong(-7);
        final Integer y = Integer.fromLong(2);

        final Integer quotient = x.div(y);
        final Integer remainder = x.rem(y);

        final boolean check = quotient.equals(Integer.fromLong(-4))
                && remainder.equals(Integer.fromLong(1));
        if (!check) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toStringOverride() {
        final BiConsumer<Integer, String> checkToString = (input, expected) -> {
            final boolean check = String.valueOf(input).equals(expected);
            if (!check) throw new IllegalStateException("Check failed.");
        };

        checkToString.accept(Integer.fromLong(0), "0");
        checkToString.accept(Integer.parse("+0"), "0");
        checkToString.accept(Integer.parse("-0"), "0");
        checkToString.accept(Integer.parse("+42"), "42");
        checkToString.accept(Integer.fromLong(-42), "-42");
        checkToString.accept(Integer.parse("00042"), "42");
        checkToString.accept(Integer.parse("+00042"), "42");
        checkToString.accept(Integer.parse("-00042"), "-42");
    }

    @Test
    void toLong() {
        final Integer x = Integer.fromLong(42);
        final long result = x.toLong();
        if (result != 42L) throw new IllegalStateException("Check failed.");

        final Integer y = Integer.parse("99999999999999999999");
        try {
            y.toLong();
            throw new IllegalStateException("Check failed.");
        } catch (ArithmeticException ignored) {
        }
    }

    @Test
    void toInt() {
        final Integer x = Integer.fromLong(42);
        final int result = x.toInt();
        if (result != 42) throw new IllegalStateException("Check failed.");

        final Integer y = Integer.parse("9999999999");
        try {
            y.toInt();
            throw new IllegalStateException("Check failed.");
        } catch (ArithmeticException ignored) {
        }
    }
}
