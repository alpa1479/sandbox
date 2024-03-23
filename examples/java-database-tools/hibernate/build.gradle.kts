dependencies {
    implementation(project(":examples:java-database-tools-starter"))
    implementation("org.hibernate.orm:hibernate-core")
    implementation("p6spy:p6spy")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    compileOnly("org.mapstruct:mapstruct")
    annotationProcessor("org.mapstruct:mapstruct-processor")
}
