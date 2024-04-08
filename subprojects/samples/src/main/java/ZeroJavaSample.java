package org.kotools.types;

class ZeroJavaSample {
    void toByte() {
        final byte number = Zero.INSTANCE.toByte();
        System.out.println(number); // 0
    } // END

    void toStringSample() {
        final String message = Zero.INSTANCE.toString();
        System.out.println(message); // 0
    } // END
}
