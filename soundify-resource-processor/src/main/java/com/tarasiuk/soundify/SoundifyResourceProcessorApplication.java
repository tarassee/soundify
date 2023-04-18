package com.tarasiuk.soundify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SoundifyResourceProcessorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoundifyResourceProcessorApplication.class, args);
    }

}
