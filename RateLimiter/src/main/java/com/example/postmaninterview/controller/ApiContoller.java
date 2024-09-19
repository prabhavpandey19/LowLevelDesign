package com.example.postmaninterview.controller;

import com.example.postmaninterview.Annotations.Ratelimit;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiContoller {


    @GetMapping("/test")
    @Ratelimit
    public String test() {
        return "Api Call successful";
    }
}
