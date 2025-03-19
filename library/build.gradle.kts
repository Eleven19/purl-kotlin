import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "io.eleven19.kotlin.purl"
version = "0.1.0"

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            explicitApi()
            dependencies {
                implementation(libs.ditchoom.buffer)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "io.eleven19.kotlin.purl"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "purl", version.toString())

    pom {
        name = "Package URL Kotlin"
        description = "Package URL implementation for Kotlin."
        inceptionYear = "2025"
        url = "https://github.com/Eleven19/purl-kotlin/"
        licenses {
            license {
                name = "Apache-2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "DamianReeves"
                name = "Damian Reeves"
                url = "https://github.com/DamianReeves"
            }
        }
        scm {
            url = "https://github.com/Eleven19/purl-kotlin"
            connection = "scm:git:git://github.com/Eleven19/purl-kotlin.git"
            developerConnection = "scm:git:"
        }
    }
}
