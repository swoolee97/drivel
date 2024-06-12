package com.ebiz.drivel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DrivelApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrivelApplication.class, args);
    }

}
