dependencies {
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("org.springframework.shell:spring-shell-starter")

    implementation("org.flywaydb:flyway-core")
    implementation("org.hibernate.orm:hibernate-core")
    implementation("org.postgresql:postgresql")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}
