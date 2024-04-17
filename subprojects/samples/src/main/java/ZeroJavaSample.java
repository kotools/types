package org.kotools.types;

class ZeroJavaSample {
    void toByte() {
        final Zero zero = new Zero();
        final byte number = zero.toByte();
        System.out.println(number); // 0
    } // END

    void toStringSample() {
        final Zero zero = new Zero();
        final String message = zero.toString();
        System.out.println(message); // 0
    } // END
}
