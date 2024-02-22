plugins {
    alias(libs.plugins.hivemq.extension)
    alias(libs.plugins.defaults)
    alias(libs.plugins.license)
}

group = "eu.wepa.hivemqextensions"
description = "HiveMQ Extension which collect Metrics Per topic."

hivemqExtension {
    name.set("Metrics Per topic Extension")
    author.set("MaibornWolff GmbH")
    priority.set(1000)
    startPriority.set(1000)
    mainClass.set("$group.metricspertopic.TopicsMetricsExtensionMain")
    sdkVersion.set(libs.versions.hivemq.extensionSdk)

    resources {
        from("LICENSE")
    }
}

dependencies {
    implementation(libs.jetbrains.annotations)
    implementation(libs.jaxb.api)
    runtimeOnly(libs.jaxb.impl)
}

@Suppress("UnstableApiUsage")
testing {
    suites {
        withType<JvmTestSuite> {
            useJUnitJupiter(libs.versions.junit.jupiter)
        }
        "test"(JvmTestSuite::class) {
            dependencies {
                implementation(libs.mockito)
            }
        }
        "integrationTest"(JvmTestSuite::class) {
            dependencies {
                compileOnly(libs.jetbrains.annotations)
                implementation(libs.jaxb.api)
                runtimeOnly(libs.jaxb.impl)
                implementation(libs.testcontainers.junitJupiter)
                implementation(libs.testcontainers.hivemq)
            }
        }
    }
}

/* ******************** checks ******************** */

license {
    header = rootDir.resolve("HEADER")
    mapping("java", "SLASHSTAR_STYLE")
}

/* ******************** debugging ******************** */

tasks.runHivemqWithExtension {
    debugOptions {
        enabled.set(false)
    }
}
