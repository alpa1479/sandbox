dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-jdbc")

    api("org.postgresql:postgresql")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor")
}
