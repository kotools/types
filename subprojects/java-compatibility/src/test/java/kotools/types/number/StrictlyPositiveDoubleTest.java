package kotools.types.number;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class StrictlyPositiveDoubleTest {
    @Test
    public void constructor() {
        final Random random = new Random();
        final double origin = 0.0, bound = Double.MAX_VALUE;
        final double value = random.nextDouble(origin, bound);
        new StrictlyPositiveDouble(value);
    }
}
