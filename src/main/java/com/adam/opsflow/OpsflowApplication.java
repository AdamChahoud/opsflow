package com.adam.opsflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class OpsflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpsflowApplication.class, args);
    }

}
