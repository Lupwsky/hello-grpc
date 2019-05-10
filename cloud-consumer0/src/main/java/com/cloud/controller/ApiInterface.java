package com.cloud.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author v_pwlu 2019/5/10
 */
@FeignClient("cloud-provider0-dev")
public interface ApiInterface {

    @GetMapping("/dc")
    String getDc();
}
