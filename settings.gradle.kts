rootProject.name = "sandbox"

pluginManagement {
    val springDependencyManagement: String by settings
    val springframeworkBoot: String by settings

    plugins {
        id("io.spring.dependency-management") version springDependencyManagement
        id("org.springframework.boot") version springframeworkBoot
    }
}

include("examples:java-database-tools:jdbc")
include("examples:java-database-tools:spring-jdbc")
include("examples:java-database-tools:hibernate")
include("examples:java-database-tools:spring-data-jpa")
