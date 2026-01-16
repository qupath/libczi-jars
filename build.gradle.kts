plugins {
    `maven-publish`
}

group = "io.github.qupath"
version = (findProperty("libCZIVersion") as String?) ?: "0.67.4-SNAPSHOT"

publishing {
    repositories {
        maven {
            name = "SciJava"
            val releasesRepoUrl = uri("https://maven.scijava.org/content/repositories/releases")
            val snapshotsRepoUrl = uri("https://maven.scijava.org/content/repositories/snapshots")

            // Use: gradle -Prelease publish
            url = if (project.hasProperty("release")) {
                releasesRepoUrl
            } else {
                snapshotsRepoUrl
            }

            credentials {
                username = System.getenv("MAVEN_USER")
                password = System.getenv("MAVEN_PASS")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = group.toString()
            version = version.toString()
            artifact(file("libCZI-natives.jar"))
            artifact(file("libCZI-natives-macos-aarch64.jar")) {
                classifier = "macos-aarch64"
            }
            artifact(file("libCZI-natives-macos-x86-64.jar")) {
                classifier = "macos-x86_64"
            }
            artifact(file("libCZI-natives-windows-x86-64.jar")) {
                classifier = "windows-x86_64"
            }
            artifact(file("libCZI-natives-linux-x86-64.jar")) {
                classifier = "linux-x86_64"
            }
            pom {
                name = "libCZI"
                description = "An Open Source Cross-Platform C++ library to read and write CZI."
                url = "https://zeiss.github.io/libczi/"
                licenses {
                    license {
                        name = "LGPL"
                        url = "https://www.gnu.org/licenses/lgpl-3.0.en.html"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/ZEISS/libczi.git"
                    developerConnection = "scm:git:ssh://github.com/ZEISS/libczi.git"
                    url = "https://zeiss.github.io/libczi/"
                }
            }
        }

    }
}
