dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation(project(":examples:java-database-tools-starter"))

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    compileOnly("org.mapstruct:mapstruct")
    annotationProcessor("org.mapstruct:mapstruct-processor")
}
