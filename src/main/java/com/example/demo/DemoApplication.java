package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The type Demo application.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo.controllers", "com.example.demo.service",
                               "com.example.demo.repository", "com.example.demo.dao",
                               "com.example.demo.exeptions"})
@EnableWebMvc
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}
