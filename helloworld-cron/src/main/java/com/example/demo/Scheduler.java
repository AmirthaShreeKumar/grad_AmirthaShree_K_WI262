package com.example.demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Scheduled(cron = "*/10 * * * * *")
    public void sayHello() {
        System.out.println("Hello World");
    }
}

