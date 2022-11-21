package com.grove.tfb_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TfbBackendApplication {



    public static void main(String[] args) {
        SpringApplication.run(TfbBackendApplication.class, args);
    }

}
