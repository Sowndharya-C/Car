package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

    @SpringBootApplication
    @EntityScan(basePackages = "com")
    public class SpringBootExample {
        public static void main(String[] args) {
            SpringApplication.run(SpringBootExample.class, args);
        }
    }

