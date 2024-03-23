dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(project(":examples:java-database-tools-starter"))
    implementation("net.ttddyy:datasource-proxy")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    compileOnly("org.mapstruct:mapstruct")
    annotationProcessor("org.mapstruct:mapstruct-processor")
}
