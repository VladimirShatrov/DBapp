package com.example.dbapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class DBappApplication {

    public static void main(String[] args) {
        SpringApplication.run(DBappApplication.class, args);
    }
}
