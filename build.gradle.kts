plugins {
    alias(libs.plugins.hivemq.extension)
    alias(libs.plugins.defaults)
    alias(libs.plugins.license)
}

group = "com.wepa.hivemqextensions"
description = "HiveMQ Extension which collect Metrics Per topic."

hivemqExtension {
    name.set("Metrics Per topic Extension")
    author.set("MaibornWolff GmbH")
    priority.set(1000)
    startPriority.set(1000)
    mainClass.set("$group.metricspertopic.TopicsMetricsMain")
    sdkVersion.set("$version")

    resources {
        from("LICENSE")
    }
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
                implementation(libs.hivemq.mqttClient)
                implementation(libs.testcontainers.junitJupiter)
                implementation(libs.testcontainers.hivemq)
                runtimeOnly(libs.logback.classic)
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

tasks.prepareHivemqHome {
    hivemqHomeDirectory.set(file("/your/path/to/hivemq-<VERSION>"))
}

tasks.runHivemqWithExtension {
    debugOptions {
        enabled.set(false)
    }
}
