package com.tarasiuk.soundify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SoundifyApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoundifyApiGatewayApplication.class, args);
    }

}
