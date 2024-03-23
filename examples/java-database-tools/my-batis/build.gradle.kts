dependencies {
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter")
    implementation(project(":examples:java-database-tools-starter"))

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    compileOnly("org.mapstruct:mapstruct")
    annotationProcessor("org.mapstruct:mapstruct-processor")
}
