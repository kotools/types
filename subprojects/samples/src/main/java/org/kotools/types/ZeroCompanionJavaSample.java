package org.kotools.types;

class ZeroCompanionJavaSample {
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
}
