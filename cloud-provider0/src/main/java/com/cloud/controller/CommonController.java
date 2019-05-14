package com.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/7/27
 */
@Slf4j
@RestController
public class CommonController {

    private final DiscoveryClient discoveryClient;


    @Autowired
    public CommonController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/get/username")
    public String getUsername() {
        return "username-from-provider-0";
    }

    @GetMapping("/get/username/from/9020")
    public String getUsernameFrom9020(HttpServletRequest request) {
        String myHeader = request.getHeader("MY_HEADER");
        log.info("myHeader = {}", myHeader);

        String myParam = request.getParameter("MY_PARAM");
        log.info("myParam = {}", myParam);
        return "username-from-9020";
    }

    @GetMapping("/dc")
    public String dc() {
        String services = "Services: " + discoveryClient.getServices();
        log.info("{}", services);
        return services;
    }
}
