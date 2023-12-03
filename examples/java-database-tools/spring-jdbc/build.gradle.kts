dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("org.springframework.shell:spring-shell-starter")

    implementation("org.postgresql:postgresql")
    implementation(files("libs/log4jdbc4-1.3.jar"))

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}
