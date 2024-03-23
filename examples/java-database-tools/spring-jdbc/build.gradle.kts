dependencies {
    implementation(project(":examples:java-database-tools-starter"))
    implementation(files("libs/log4jdbc4-1.3.jar"))

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    compileOnly("org.mapstruct:mapstruct")
    annotationProcessor("org.mapstruct:mapstruct-processor")
}
