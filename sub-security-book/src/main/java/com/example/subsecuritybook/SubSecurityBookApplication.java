package com.example.subsecuritybook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.example.subsecuritybook.mapper")
@SpringBootApplication
public class SubSecurityBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubSecurityBookApplication.class, args);
    }
}
