object Kotlin {
    private const val GROUP = "org.jetbrains.kotlin"

    val coroutines: String
        get() = "${GROUP}x:kotlinx-coroutines-core:[1.5, 1.6["
    val version: String get() = "1.5.32"
}

object Library {
    val csv: String get() = "com.github.doyaaaaaken:kotlin-csv-jvm:[1.2, 1.3["
}
