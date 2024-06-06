package org.kotools.types;

class ZeroCompanionJavaSample {
    void pattern() {
        final String pattern = Zero.PATTERN;
        System.out.println(pattern); // ^[+-]?0+(?:\.0+)?$
    } // END

    void fromByte() {
        final byte number = 0;
        boolean isSuccess;
        try {
            Zero.fromByte(number); // TABS: 1
            isSuccess = true; // TABS: 1
        } catch (final IllegalArgumentException exception) {
            isSuccess = false; // TABS: 1
        }
        System.out.println(isSuccess); // true
    } // END

    void fromByteOrNull() {
        final byte number = 0;
        final Zero zero = Zero.fromByteOrNull(number);
        System.out.println(zero != null); // true
    } // END

    void fromShort() {
        final short number = 0;
        boolean isSuccess;
        try {
            Zero.fromShort(number); // TABS: 1
            isSuccess = true; // TABS: 1
        } catch (final IllegalArgumentException exception) {
            isSuccess = false; // TABS: 1
        }
        System.out.println(isSuccess); // true
    } // END

    void fromShortOrNull() {
        final short number = 0;
        final Zero zero = Zero.fromShortOrNull(number);
        System.out.println(zero != null); // true
    } // END

    void fromInt() {
        boolean isSuccess;
        try {
            Zero.fromInt(0); // TABS: 1
            isSuccess = true; // TABS: 1
        } catch (final IllegalArgumentException exception) {
            isSuccess = false; // TABS: 1
        }
        System.out.println(isSuccess); // true
    } // END

    void fromIntOrNull() {
        final Zero zero = Zero.fromIntOrNull(0);
        System.out.println(zero != null); // true
    } // END

    void fromLong() {
        boolean isSuccess;
        try {
            Zero.fromLong(0L); // TABS: 1
            isSuccess = true; // TABS: 1
        } catch (final IllegalArgumentException exception) {
            isSuccess = false; // TABS: 1
        }
        System.out.println(isSuccess); // true
    } // END

    void fromLongOrNull() {
        final Zero zero = Zero.fromLongOrNull(0L);
        System.out.println(zero != null); // true
    } // END

    void fromFloat() {
        boolean isSuccess;
        try {
            Zero.fromFloat(0f); // TABS: 1
            isSuccess = true; // TABS: 1
        } catch (final IllegalArgumentException exception) {
            isSuccess = false; // TABS: 1
        }
        System.out.println(isSuccess); // true
    } // END

    void fromFloatOrNull() {
        final Zero zero = Zero.fromFloatOrNull(0f);
        System.out.println(zero != null); // true
    } // END

    void fromDouble() {
        boolean isSuccess;
        try {
            Zero.fromDouble(0.0); // TABS: 1
            isSuccess = true; // TABS: 1
        } catch (final IllegalArgumentException exception) {
            isSuccess = false; // TABS: 1
        }
        System.out.println(isSuccess); // true
    } // END

    void fromDoubleOrNull() {
        final Zero zero = Zero.fromDoubleOrNull(0.0);
        System.out.println(zero != null); // true
    } // END
}
