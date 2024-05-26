package kotools.types.number;

import java.util.Random;

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

    void createOrNull() {
        final Random random = new Random();
        final Number number = random.nextInt(Integer.MIN_VALUE, 0);
        final StrictlyNegativeDouble result =
                StrictlyNegativeDouble.Companion.createOrNull(number); // TABS: 2
        System.out.println(result != null); // true
    } // END
}
