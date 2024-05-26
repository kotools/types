package kotools.types.number;

class PositiveIntCompanionJavaSample {
    void create() {
        final PositiveInt number = PositiveInt.Companion.create(23);
        System.out.println(number); // 23
    } // END

    void createOrNull() {
        final PositiveInt number = PositiveInt.Companion.createOrNull(23);
        System.out.println(number); // 23
    } // END
}
