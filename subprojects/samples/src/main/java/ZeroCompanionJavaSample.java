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
}
