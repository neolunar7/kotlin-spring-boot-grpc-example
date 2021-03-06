import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    kotlin("jvm") version "1.3.61"
    idea
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.springframework.boot") version "2.2.4.RELEASE" apply false
    kotlin("plugin.spring") version "1.3.61" apply false
    java
    id("com.google.protobuf") version "0.8.11" apply false
    id("org.jlleitschuh.gradle.ktlint") version "9.1.0" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("idea")
        plugin("io.spring.dependency-management")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    configure<DependencyManagementExtension> {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    configure<KtlintExtension> {
        disabledRules.add("import-ordering")
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    group = "com.hojongs"
    version = ""

    dependencies {
        implementation(kotlin("reflect"))
        implementation(kotlin("stdlib-jdk8"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        testImplementation("io.projectreactor:reactor-test")
        testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation("io.kotlintest:kotlintest-assertions:3.3.2")
        testImplementation("io.mockk:mockk:1.9.3")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "1.8"
            }
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
}
