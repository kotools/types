package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class ZeroIntKotlinSample {
    fun serialization() {
        val encoded: String = Json.encodeToString(ZeroInt)
        println(encoded) // 0
        val decoded: ZeroInt = Json.decodeFromString(encoded)
        println(decoded === ZeroInt) // true
    } // END
}
