val fileFilter = listOf(
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "android/**/*.*"
)

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    group = "Reporting"
    description = "Execute unit tests and generate Jacoco coverage report."

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    sourceDirectories.setFrom(layout.projectDirectory.dir("src/main"))
    classDirectories.setFrom(files(
        fileTree(layout.buildDirectory.dir("intermediates/javac/")) { exclude(fileFilter) },
        fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/")) { exclude(fileFilter) }
    ))
    executionData.setFrom(
        files(fileTree(layout.buildDirectory) {
            include(listOf("**/*.exec", "**/*.ec"))
            exclude("test-results/**")
        }
    ))
}

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}