package kotools.types.number;

class StrictlyNegativeDoubleCompanionJavaSample {
    void create() {
        final Number number = -23;
        boolean isSuccess;
        try {
            StrictlyNegativeDouble.Companion.create(number); // TABS: 1
            isSuccess = true; // TABS: 1
        } catch (final IllegalArgumentException exception) {
            isSuccess = false; // TABS: 1
        }
        System.out.println(isSuccess); // true
    } // END
}
