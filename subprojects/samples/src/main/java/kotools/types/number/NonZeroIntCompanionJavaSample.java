package kotools.types.number;

class NonZeroIntCompanionJavaSample {
    void create() {
        final NonZeroInt number = NonZeroInt.Companion.create(23);
        System.out.println(number); // 23
    } // END

    void createOrNull() {
        final NonZeroInt number = NonZeroInt.Companion.createOrNull(23);
        System.out.println(number); // 23
    } // END
}
