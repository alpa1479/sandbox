import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES

plugins {
    idea
    id("io.spring.dependency-management")
    id("org.springframework.boot") apply false
}

idea {
    project {
        languageLevel = IdeaLanguageLevel(21)
    }
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

allprojects {
    group = "edu.sandbox"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    val lombok: String by project
    val mapStruct: String by project
    val springShellStarter: String by project
    val hibernate: String by project
    val log4jdbcRemix: String by project
    val datasourceProxy: String by project
    val p6spy: String by project
    val myBatisStarter: String by project

    apply(plugin = "io.spring.dependency-management")
    dependencyManagement {
        dependencies {
            imports {
                mavenBom(BOM_COORDINATES)
            }
            dependency("org.projectlombok:lombok:$lombok")
            dependency("org.mapstruct:mapstruct:$mapStruct")
            dependency("org.mapstruct:mapstruct-processor:$mapStruct")
            dependency("org.springframework.shell:spring-shell-starter:$springShellStarter")
            dependency("org.mybatis.spring.boot:mybatis-spring-boot-starter:$myBatisStarter")
            dependency("org.hibernate:hibernate-core:$hibernate")
            dependency("org.lazyluke:log4jdbc-remix:$log4jdbcRemix")
            dependency("net.ttddyy:datasource-proxy:$datasourceProxy")
            dependency("p6spy:p6spy:$p6spy")
        }
    }

    configurations.all {
        resolutionStrategy {
            failOnVersionConflict()
        }
    }
}

subprojects {
    plugins.apply(JavaLibraryPlugin::class.java)
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-Xlint:all,-serial,-processing"))
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging.showExceptions = true
        reports {
            junitXml.required.set(true)
            html.required.set(true)
        }
    }
}

tasks {
    val managedVersions by registering {
        doLast {
            project.extensions.getByType<DependencyManagementExtension>()
                    .managedVersions
                    .toSortedMap()
                    .map { "${it.key}:${it.value}" }
                    .forEach(::println)
        }
    }
}
