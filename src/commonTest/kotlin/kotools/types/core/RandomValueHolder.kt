package kotools.types.core

import kotools.types.StrictlyPositiveInt
import kotools.types.StrictlyPositiveIntOrNull
import kotlin.random.Random

interface RandomValueHolder {
    val randomInt: Int get() = Random.nextInt()

    val randomString: String
        get() {
            val characters: List<Char> = ('a'..'z') + ('A'..'Z')
            val lengths: List<StrictlyPositiveInt> = listOf(2, 4, 8, 16, 32, 64)
                .mapNotNull(::StrictlyPositiveIntOrNull)
            return lengths.random()
                .let { 1..it.value }
                .map { characters.random() }
                .joinToString("")
        }
}
