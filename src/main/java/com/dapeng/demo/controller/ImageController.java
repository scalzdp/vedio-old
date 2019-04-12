package com.dapeng.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @RequestMapping("/welcome")
    public String home(@RequestParam String name) {
        return name+" 欢迎您，这里有一些话想对你说:    ";
    }
}
