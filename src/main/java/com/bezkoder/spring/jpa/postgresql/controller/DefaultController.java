package com.bezkoder.spring.jpa.postgresql.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class DefaultController {

    @GetMapping
    public String home() {
        return "Welcome to localhost:8080!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}