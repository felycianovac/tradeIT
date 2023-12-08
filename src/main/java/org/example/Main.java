package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


import java.io.IOException;
@SpringBootApplication()
public class Main {
    public String PORT = System.getenv("PORT");
    public static void main(String[] args) throws  IOException {
        SpringApplication.run(Main.class, args);
    }
}