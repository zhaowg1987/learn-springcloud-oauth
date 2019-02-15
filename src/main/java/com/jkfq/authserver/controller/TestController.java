package com.jkfq.authserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${DESCRIPTION}
 *
 * @Author
 * @create 2019-02-01
 **/
@Slf4j
@RestController
@EnableResourceServer
public class TestController {
    @RequestMapping("/sayHi")
    public String sayHi(@RequestParam String name) {
        log.info("访问测试。。。");
        return name + " hello!";
    }


    @RequestMapping("/sayHi2")
    public String sayHi2() {
        log.info("访问测试。。。");
        return " hello11111111!";
    }
}
