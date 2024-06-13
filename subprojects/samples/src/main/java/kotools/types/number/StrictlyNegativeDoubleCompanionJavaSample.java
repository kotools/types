package kotools.types.number;

import java.util.Random;

class StrictlyNegativeDoubleCompanionJavaSample {
    void create() {
        final Number number = -23;
        boolean isSuccess;
        try {
            StrictlyNegativeDouble.Companion.create(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        System.out.println(isSuccess); // true
    } // END

    void createOrNull() {
        final Random random = new Random();
        final Number number = random.nextInt(Integer.MIN_VALUE, 0);
        final StrictlyNegativeDouble result =
                StrictlyNegativeDouble.Companion.createOrNull(number);
        System.out.println(result != null); // true
    } // END
}
