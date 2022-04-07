package com.example.subsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.subsecurity.mapper")
@SpringBootApplication
public class SubSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubSecurityApplication.class, args);
    }

}
