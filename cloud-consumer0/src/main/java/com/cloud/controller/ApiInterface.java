package com.cloud.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author v_pwlu 2019/5/10
 */
@FeignClient("cloud-provider0-dev")
public interface ApiInterface {

    @GetMapping("/get/username")
    String getDc();


    // getDc()
    // cloud-provider0-dev/get/username
    @GetMapping("/get/username")
    String getUserName();
}
