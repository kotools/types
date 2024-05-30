package kotools.types.number;

class StrictlyNegativeDoubleJavaSample {
    void equalsOverride() {
        final int number = -23;
        final StrictlyNegativeDouble first =
                StrictlyNegativeDouble.Companion.create(number); // TABS: 2
        final StrictlyNegativeDouble second =
                StrictlyNegativeDouble.Companion.create(number); // TABS: 2
        final boolean result = first.equals(second);
        System.out.println(result); // true
    } // END

    void hashCodeOverride() {
        final int number = -23;
        final StrictlyNegativeDouble first =
                StrictlyNegativeDouble.Companion.create(number); // TABS: 2
        final StrictlyNegativeDouble second =
                StrictlyNegativeDouble.Companion.create(number); // TABS: 2
        final boolean result = first.hashCode() == second.hashCode();
        System.out.println(result); // true
    } // END

    void toDouble() {
        final StrictlyNegativeDouble number =
                StrictlyNegativeDouble.Companion.create(-7); // TABS: 2
        final double result = number.toDouble();
        System.out.println(result); // -7.0
    } // END
}
