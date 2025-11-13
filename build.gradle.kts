import java.time.Duration

plugins {
    alias(libs.plugins.convention.base.root)
    alias(libs.plugins.convention.documentation.root)
    alias(libs.plugins.convention.publication.root)
    alias(libs.plugins.nmcp) apply false
    alias(libs.plugins.nmcp.aggregation)
}

nmcpAggregation {
    centralPortal {
        username = System.getenv("SONATYPE_PORTAL_USERNAME")
        password = System.getenv("SONATYPE_PORTAL_PASSWORD")
        validationTimeout = Duration.ZERO
        publishingType = "USER_MANAGED"
    }
}

dependencies {
    dokkaHtmlMultiModulePlugin(libs.dokka.versioning)

    nmcpAggregation(project(":types"))
    nmcpAggregation(project(":types-internal"))
    nmcpAggregation(project(":types-kotlinx-serialization"))
}
