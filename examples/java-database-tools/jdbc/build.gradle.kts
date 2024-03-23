dependencies {
    implementation(project(":examples:java-database-tools-starter"))
    implementation("org.lazyluke:log4jdbc-remix")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    compileOnly("org.mapstruct:mapstruct")
    annotationProcessor("org.mapstruct:mapstruct-processor")
}
