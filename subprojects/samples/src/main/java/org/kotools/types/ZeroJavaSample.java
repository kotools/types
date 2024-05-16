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

    void compareToByte() {
        final Zero zero = new Zero();
        final byte other = 1;
        final boolean result = zero.compareTo(other) < 0;
        System.out.println(result); // true
    } // END

    void compareToShort() {
        final Zero zero = new Zero();
        final short other = 1;
        final boolean result = zero.compareTo(other) < 0;
        System.out.println(result); // true
    } // END

    void compareToInt() {
        final Zero zero = new Zero();
        final int other = 1;
        final boolean result = zero.compareTo(other) < 0;
        System.out.println(result); // true
    } // END

    void compareToLong() {
        final Zero zero = new Zero();
        final long other = 1;
        final boolean result = zero.compareTo(other) < 0;
        System.out.println(result); // true
    } // END

    void toByte() {
        final Zero zero = new Zero();
        final byte number = zero.toByte();
        System.out.println(number); // 0
    } // END

    void toShort() {
        final short number = new Zero()
                .toShort(); // TABS: 2
        final short expected = 0;
        System.out.println(number == expected); // true
    } // END

    void toInt() {
        final int number = new Zero()
                .toInt(); // TABS: 2
        final int expected = 0;
        System.out.println(number == expected); // true
    } // END

    void toLong() {
        final long number = new Zero()
                .toLong(); // TABS: 2
        final long expected = 0;
        System.out.println(number == expected); // true
    } // END

    void toFloat() {
        final float number = new Zero()
                .toFloat(); // TABS: 2
        final float expected = 0;
        System.out.println(number == expected); // true
    } // END

    void toDouble() {
        final double number = new Zero()
                .toDouble(); // TABS: 2
        final double expected = 0.0;
        System.out.println(number == expected); // true
    } // END

    void toStringSample() {
        final Zero zero = new Zero();
        final String message = zero.toString();
        System.out.println(message); // 0
    } // END
}
