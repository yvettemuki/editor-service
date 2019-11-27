package com.yvettemuki.editorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class EditorServiceApplication {

    @CrossOrigin
    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    String home() {
        return "hello world!";
    }

    public static void main(String[] args) {
        SpringApplication.run(EditorServiceApplication.class, args);
    }

}
