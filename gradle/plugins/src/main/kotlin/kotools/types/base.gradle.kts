package kotools.types

group = "org.kotools"

repositories.mavenCentral()

tasks.register("unit")

tasks.register("version").configure {
    description = "Displays the project's version."
    doLast { println(version) }
}
