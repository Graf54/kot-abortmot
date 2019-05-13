import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.junit.platform.gradle.plugin.EnginesExtension
import org.junit.platform.gradle.plugin.FiltersExtension
import org.junit.platform.gradle.plugin.JUnitPlatformExtension

group = "org.kotlerning"
version = "2.0-SNAPSHOT"


buildscript {
    dependencies {
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$1.3.21")
    }
}
/*configure {
    filters {
        engines {
            include("spek")
        }
    }
}*/

repositories {
    mavenLocal()
    jcenter()
    mavenCentral()
}
// extension for configuration

plugins {
    `java-library`
    java
    val kotlinVersion = "1.3.21"
    kotlin("jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    id("org.springframework.boot") version "2.1.2.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

allOpen {
    annotation("javax.persistence.Entity")
}


dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.telegram:telegrambots:4.1.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.1.3.RELEASE")
    implementation("org.springframework.boot:spring-boot-configuration-processor:2.1.3.RELEASE")
    /*implementation("org.hibernate:hibernate-core:5.3.7.Final")
    implementation("org.hibernate:hibernate-ehcache:5.3.7.Final"){
        exclude(group = "net.sf.ehcache", module = "ehcache")
    }
    implementation("org.ehcache:ehcache:3.4.0")
    implementation("javax.cache:cache-api")*/
    /*implementation("org.hibernate:hibernate-entitymanager:5.3.7.Final")*/
    implementation("com.h2database:h2:1.4.197")
    implementation("io.github.microutils:kotlin-logging:1.6.24")
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.spek:spek-api:1.1.5") {
        exclude(group = "org.jetbrains.kotlin")
    }
    testRuntime("org.jetbrains.spek:spek-junit-platform-engine:1.1.5") {
        exclude(group = "org.junit.platform")
        exclude(group = "org.jetbrains.kotlin")
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"

//   FOR TEST

    fun JUnitPlatformExtension.filters(setup: FiltersExtension.() -> Unit) {
        when (this) {
            is ExtensionAware -> extensions.getByType(FiltersExtension::class.java).setup()
            else -> throw Exception("${this::class} must be an instance of ExtensionAware")
        }
    }

    fun FiltersExtension.engines(setup: EnginesExtension.() -> Unit) {
        when (this) {
            is ExtensionAware -> extensions.getByType(EnginesExtension::class.java).setup()
            else -> throw Exception("${this::class} must be an instance of ExtensionAware")
        }
    }


}