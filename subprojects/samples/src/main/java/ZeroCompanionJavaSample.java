package org.kotools.types;

class ZeroCompanionJavaSample {
    void fromByteOrNull() {
        final byte number = 0;
        final Zero zero = Zero.fromByteOrNull(number);
        System.out.println(zero != null); // true
    } // END
}
