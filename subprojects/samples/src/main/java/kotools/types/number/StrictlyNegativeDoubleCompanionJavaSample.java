package kotools.types.number;

class StrictlyNegativeDoubleCompanionJavaSample {
    void create() {
        // The following code prints 'Success'.
        try {
            StrictlyNegativeDouble.Companion.create(-23); // TABS: 1
            System.out.println("Success"); // TABS: 1
        } catch (IllegalArgumentException exception) {
            final String reason = exception.getMessage(); // TABS: 1
            System.out.println("Failure: " + reason); // TABS: 1
        }
    } // END
}
