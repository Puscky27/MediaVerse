package com.university.mediaverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MediaVerse {

    private static ApplicationContext appContext;

    public static void main(String[] args) {
        SpringApplication.run(MediaVerse.class, args);
    }

    public static ApplicationContext getAppContext() {
        return appContext;
    }
}
