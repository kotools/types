plugins {
    alias(libs.plugins.convention.base.root)
    alias(libs.plugins.convention.documentation.root)
    alias(libs.plugins.convention.publication.root)
    alias(libs.plugins.maven.central.publishing)
}

nmcpAggregation {
    centralPortal {
        username = System.getenv("SONATYPE_PORTAL_USERNAME")
        password = System.getenv("SONATYPE_PORTAL_PASSWORD")
        publishingType = "USER_MANAGED"
    }
    publishAllProjectsProbablyBreakingProjectIsolation()
}

dependencies.dokkaHtmlMultiModulePlugin(libs.dokka.versioning)
