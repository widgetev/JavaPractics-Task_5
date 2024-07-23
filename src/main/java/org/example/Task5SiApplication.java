package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Task5SiApplication {

    public static void main(String[] args) {
        //@ClassRule
        //public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1");

        SpringApplication.run(Task5SiApplication.class, args);
    }

}
