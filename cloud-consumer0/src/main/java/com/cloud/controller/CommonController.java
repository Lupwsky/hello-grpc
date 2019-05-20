package com.cloud.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/7/27
 */
@RestController
@Slf4j
public class CommonController {

    /**
     * http://blog.didispace.com/spring-cloud-starter-dalston-2-2/
     */
    private final LoadBalancerClient loadBalancerClient;
    private final RestTemplate restTemplate;

    private final ApiInterface apiInterface;

    @Autowired
    public CommonController(LoadBalancerClient loadBalancerClient,
                            RestTemplate restTemplate,
                            ApiInterface apiInterface) {
        this.loadBalancerClient = loadBalancerClient;
        this.restTemplate = restTemplate;
        this.apiInterface = apiInterface;
    }


    @GetMapping("/get/username")
    public void getUsername() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("cloud-provider1-dev");
        // String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/get/username";
        // https://blog.csdn.net/november22/article/details/54612454
        String url = "http://" + serviceInstance.getServiceId() + ":" + serviceInstance.getPort() + "/get/username";
        log.info("url = {}", url);
        for (int i = 0; i< 10; i++) {
            String username = restTemplate.getForObject(url, String.class);
            log.info("username = {}", username);
        }
    }


    @GetMapping("/get/username/with/ribbon")
    public void getUsernameWithRibbon() {
        String username = restTemplate.getForObject("http://cloud-provider1-dev/get/username", String.class);
        log.info("username = {}", username);
    }


    @GetMapping("/dc")
    public String getDc() {
        return apiInterface.getUserName();
    }
}
