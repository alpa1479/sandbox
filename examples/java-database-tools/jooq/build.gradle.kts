import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.*
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Target

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("org.springframework.shell:spring-shell-starter")

    implementation("org.postgresql:postgresql")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

buildscript {
    repositories {
        mavenCentral()
    }

    val jooqCodegen: String by project
    val postgresqlDriver: String by project

    dependencies {
        classpath("org.jooq:jooq-codegen:${jooqCodegen}")
        classpath("org.postgresql:postgresql:${postgresqlDriver}")
    }
}

tasks.create("generate") {
    val configuration = Configuration()
            .withJdbc(
                    Jdbc()
                            .withDriver("org.postgresql.Driver")
                            .withUrl("jdbc:postgresql://localhost:5432/library")
                            .withUsername("usr")
                            .withPassword("pwd")
            ).withGenerator(
                    Generator()
                            .withDatabase(
                                    Database()
                                            .withInputSchema("public")
                            )
                            .withGenerate(
                                    Generate()
                                            .withPojos(true)
                                            .withDaos(true)
                                            .withPojosEqualsAndHashCode(true)
                                            .withSpringAnnotations(true)
                                            .withFluentSetters(true)
//                                            .withJpaAnnotations(true)
                            )
//                            .withStrategy(
//                                    Strategy()
//                                            .withName("org.jooq.codegen.example.JPrefixGeneratorStrategy")
//                            )
                            .withTarget(
                                    Target()
                                            .withPackageName("edu.sandbox.javadatabasetools.jooq.generated")
                                            .withDirectory("$projectDir/src/main/java/")
                            )
            )

    doLast {
        GenerationTool.generate(configuration)
    }
}
