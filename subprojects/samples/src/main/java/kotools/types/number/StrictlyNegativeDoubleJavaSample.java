package kotools.types.number;

class StrictlyNegativeDoubleJavaSample {
    void equalsOverride() {
        final int number = -23;
        final StrictlyNegativeDouble first =
                StrictlyNegativeDouble.Companion.create(number);
        final StrictlyNegativeDouble second =
                StrictlyNegativeDouble.Companion.create(number);
        final boolean result = first.equals(second);
        System.out.println(result); // true
    } // END

    void hashCodeOverride() {
        final int number = -23;
        final StrictlyNegativeDouble first =
                StrictlyNegativeDouble.Companion.create(number);
        final StrictlyNegativeDouble second =
                StrictlyNegativeDouble.Companion.create(number);
        final boolean result = first.hashCode() == second.hashCode();
        System.out.println(result); // true
    } // END

    void toDouble() {
        final StrictlyNegativeDouble number =
                StrictlyNegativeDouble.Companion.create(-7);
        final double result = number.toDouble();
        System.out.println(result); // -7.0
    } // END

    void toStringOverride() {
        final StrictlyNegativeDouble number =
                StrictlyNegativeDouble.Companion.create(-23);
        final String result = number.toString();
        System.out.println(result); // -23.0
    } // END
}
