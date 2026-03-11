package com.itsconv.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ItsconvWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItsconvWebApplication.class, args);
    }

}
