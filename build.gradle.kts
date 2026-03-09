import java.text.SimpleDateFormat
import java.util.*

plugins {
    `java-library`
    `maven-publish`
    checkstyle
    signing
}

group = "com.ajthegreattt"
version = "2.0.0"

repositories {
    mavenCentral()
}

checkstyle {
    toolVersion = "10.25.0"
    config = resources.text.fromFile("src/main/resources/checkstyle.xml")
    reportsDir = file("${project.projectDir}/src/main/resources")
    isShowViolations = true
}

tasks.withType<Checkstyle>().configureEach {
    tasks.named("check") {
        dependsOn(this@configureEach)
    }

    sourceSets.main.get().java.srcDirs.forEach { sourceDir ->
        source(sourceDir)
    }

    @Suppress("UnstableApiUsage")
    isIgnoreFailures = false
    exclude("**/exclude/**/*.java")
}

tasks.named<Javadoc>("javadoc") {
    isFailOnError = false
    exclude("**/exclude/")
}

java {
    withSourcesJar()
    withJavadocJar()
}

if (JavaVersion.current().isJava8Compatible) {
    allprojects {
        tasks.withType<Javadoc>().configureEach {
            (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
        }
    }
}

publishing {
    repositories {
        maven {
            name = "CentralPortal"
            url = uri(layout.buildDirectory.dir("staging-deploy"))
        }
    }

    publications {

        create<MavenPublication>("mavenJava") {
            artifactId = "renderdoc4j"

            from(components["java"])

            pom {
                name.set("RenderDoc4J")
                description.set("A high-level, abstracted RenderDoc API wrapper for Java.")
                url.set("https://github.com/AJTheGreattt/RenderDoc4J")
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("AJTheGreattt")
                        name.set("AJ")
                        email.set("contact@ajthegreattt.com")
                        organization.set("NO&#8322; Studios")
                        organizationUrl.set("https://nightoxygenstudios.com")
                        roles.addAll("Developer", "Maintainer")
                        timezone.set("America/New_York")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/AJTheGreattt/RenderDoc4J.git")
                    developerConnection.set("scm:git:ssh://github.com/AJTheGreattt/RenderDoc4J.git")
                    url.set("https://github.com/AJTheGreattt/RenderDoc4J")
                }
            }
        }
    }
}

signing {
    val keyId = project.findProperty("signing.keyId") as? String
    val signingKey = project.findProperty("signing.key") as? String
    val signingPassword = project.findProperty("signing.password") as? String

    if (keyId != null && signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(keyId, signingKey, signingPassword)
        sign(publishing.publications["mavenJava"])
    }
}

dependencies {
    api("net.java.dev.jna:jna:5.18.1")
    implementation("org.jetbrains:annotations:24.0.0")
}

tasks.named<Jar>("jar") {
    exclude("**/exclude/", "**/checkstyle.xml")

    manifest {
        attributes(
            "Build-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date()),
            "Implementation-Version" to project.version
        )
    }
}
