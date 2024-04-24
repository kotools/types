package org.kotools.types;

class ZeroJavaSample {
    void equalsOverride() {
        final Zero first = new Zero();
        final Zero second = new Zero();
        final boolean result = first.equals(second);
        System.out.println(result); // true
    } // END

    void hashCodeOverride() {
        final int first = new Zero()
                .hashCode(); // TABS: 2
        final int second = new Zero()
                .hashCode(); // TABS: 2
        System.out.println(first == second); // true
    } // END

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