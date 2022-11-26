package kotools.types.core

import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.strictlyPositive
import kotlin.random.Random

interface RandomValueHolder {
    val randomInt: Int get() = Random.nextInt()

    val randomString: String
        get() {
            val characters: List<Char> = ('a'..'z') + ('A'..'Z')
            val lengths: List<StrictlyPositiveInt> = listOf(2, 4, 8, 16, 32, 64)
                .mapNotNull { it.strictlyPositive.getOrNull() }
            return lengths.random()
                .let { 1..it.value }
                .map { characters.random() }
                .joinToString("")
        }
}
