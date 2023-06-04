package com.tarasiuk.soundify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SoundifyResourceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoundifyResourceServiceApplication.class, args);
    }
}
