package com.Main.security.controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/contact")
    public String contact() {
        return "wowo look at contact";
    }

}
