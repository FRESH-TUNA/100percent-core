package com.main.koko_main_api.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// JSON을 반환하는 컨트롤러
@RestController
public class HelloController {
    // GET 요청에 해당하는 API를 만든다.
    @GetMapping
    public String hello() {
        return "Hello";
    }
}
