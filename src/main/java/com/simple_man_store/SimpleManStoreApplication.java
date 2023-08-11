package com.simple_man_store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimpleManStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleManStoreApplication.class, args);
    }

}
