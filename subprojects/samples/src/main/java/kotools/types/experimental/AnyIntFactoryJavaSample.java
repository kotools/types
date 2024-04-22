package kotools.types.experimental;

import kotools.types.number.AnyInt;

class AnyIntFactoryJavaSample {
    void constructorLikeInt() {
        final AnyInt number = AnyIntFactory.create(1);
        System.out.println(number); // 1
    } // END
}
