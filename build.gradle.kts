plugins {
    `java-library`
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
}

val lib_version: String = "${project.properties["version-lib"]}"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.tabooproject.org/repository/releases") }
}

dependencies {
    compileOnly("ink.ptms:nms-all:1.0.0")
    compileOnly("ink.ptms.core:v11200:11200")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))

    implementation("io.izzel:taboolib:${lib_version}:common")
    implementation("io.izzel:taboolib:${lib_version}:common-5")
    implementation("io.izzel:taboolib:${lib_version}:module-kether")
    implementation("io.izzel:taboolib:${lib_version}:module-configuration")
    implementation("io.izzel:taboolib:${lib_version}:module-database")
    implementation("io.izzel:taboolib:${lib_version}:module-ui")
    implementation("io.izzel:taboolib:${lib_version}:module-chat")
    implementation("io.izzel:taboolib:${lib_version}:module-lang")
    implementation("io.izzel:taboolib:${lib_version}:platform-bukkit")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

publishing {
    repositories {
        maven {
            url = uri("https://repo.tabooproject.org/repository/releases")
            credentials {
                username = project.findProperty("taboolibUsername").toString()
                password = project.findProperty("taboolibPassword").toString()
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
        }
    }
}