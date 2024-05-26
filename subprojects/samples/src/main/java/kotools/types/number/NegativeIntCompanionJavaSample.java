package kotools.types.number;

class NegativeIntCompanionJavaSample {
    void create() {
        final NegativeInt number = NegativeInt.Companion.create(-7);
        System.out.println(number); // -7
    } // END

    void createOrNull() {
        final NegativeInt number = NegativeInt.Companion.createOrNull(-7);
        System.out.println(number); // -7
    } // END
}
