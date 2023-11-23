/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental;

import kotools.types.number.AnyInt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class AnyIntFactoryTest {
    @Test
    public void createShouldPassWithInt() {
        final int expected = new Random()
                .nextInt();
        final AnyInt number = AnyIntFactory.create(expected);
        final int actual = number.toInt();
        Assertions.assertEquals(expected, actual);
    }
}
