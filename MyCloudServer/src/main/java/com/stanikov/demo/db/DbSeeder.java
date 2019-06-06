package com.stanikov.demo.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbSeeder implements CommandLineRunner {


    public DbSeeder() {
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Server is ready!");
    }
}
