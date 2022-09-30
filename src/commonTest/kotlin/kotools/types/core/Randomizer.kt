package kotools.types.core

import kotools.types.int.StrictlyPositiveInt
import kotools.types.int.toStrictlyPositiveIntOrNull
import kotlin.random.Random

interface Randomizer {
    val randomInt: Int get() = Random.nextInt()

    val randomString: String
        get() {
            val characters: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
            val lengths: List<StrictlyPositiveInt> = listOf(2, 4, 8, 16, 32, 64)
                .mapNotNull(Int::toStrictlyPositiveIntOrNull)
            return lengths.random()
                .let { 1..it.value }
                .map { characters.random() }
                .joinToString("")
        }
}
