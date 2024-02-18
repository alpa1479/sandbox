package edu.sandbox.javadatabasetools.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MyBatisDemo {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisDemo.class, args);
    }
}
