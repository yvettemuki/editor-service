package com.yvettemuki.editorservice;

import net.minidev.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(origins = "*",allowCredentials = "true",allowedHeaders = "content-type",methods = {})
@RequestMapping(value = "/api")
@SpringBootApplication
public class EditorServiceApplication {

    @RequestMapping(value = "/test", method = GET)
    public String home() {
        return "hello world!";
    }


    @RequestMapping(value = "/picture", method = POST)
    public Object postPicXML(@RequestBody Picture picture) {
        System.out.println(picture.getXml());
        System.out.println(picture.getHeight());
        System.out.println(picture.getWidth());
        JSONObject json = new JSONObject();
        json.put("success", true);
        return json;
    }

    public static void main(String[] args) {
        SpringApplication.run(EditorServiceApplication.class, args);
    }

}
