import java.net.URI
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.4.0"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

group = "pro.banmaster"
version = "0.0.1"

repositories {
    mavenCentral()
    maven { url = URI("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = URI("https://repo.acrylicstyle.xyz/") }
    maven { url = URI("https://repo2.acrylicstyle.xyz/") }
}

configurations.implementation {
    extendsFrom(configurations.shadow.get())
}

dependencies {
    shadow(kotlin("stdlib-jdk8"))
    shadow("xyz.acrylicstyle:java-util-all:0.11.25")
    shadow("xyz.acrylicstyle:NMSAPI-shared:0.1.48")
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    shadow(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar"))))
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs = listOf(
            "-Xjsr305=strict"
        )
    }

    withType<JavaCompile>().configureEach {
        options.encoding = "utf-8"
    }

    withType<ProcessResources> {
        filteringCharset = "UTF-8"
        from(sourceSets.main.get().resources.srcDirs) {
            include("**")

            val tokenReplacementMap = mapOf(
                "version" to project.version,
                "name" to project.rootProject.name
            )

            filter<org.apache.tools.ant.filters.ReplaceTokens>("tokens" to tokenReplacementMap)
        }

        from(projectDir) { include("LICENSE") }
    }

    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        configurations = listOf(project.configurations.shadow.get())
        archiveClassifier.set(null as String?)
        archiveFileName.set("BanMaster.jar")
        relocate("util", "pro.banmaster.libs.util")
        relocate("xyz.acrylicstyle.shared", "pro.banmaster.libs.xyz.acrylicstyle.shared")
        relocate("xyz.acrylicstyle.sql", "pro.banmaster.libs.xyz.acrylicstyle.sql")
        relocate("kotlin", "pro.banmaster.libs.kotlin")
        relocate("com.mysql", "pro.banmaster.libs.com.mysql")
        relocate("me.leoko.advancedban", "pro.banmaster.libs.me.leoko.advancedban")
    }

    withType<Jar> {
        //enabled = false
    }
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = "1.4"
}
