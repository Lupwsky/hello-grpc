package com.grpc.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
//@EnableConfigurationProperties({AutoConfigProperties.class})
public class AutoConfigController {

//    private final AutoConfigProperties autoConfigProperties;

//    @Autowired
//    public AutoConfigController(AutoConfigProperties autoConfigProperties) {
//        this.autoConfigProperties = autoConfigProperties;
//    }

    @RequestMapping(value = "/grpc/server/auto/config/test", method = RequestMethod.GET)
    public void autoConfigTest() {
//        log.info("autoConfigProperties = {}", JSONObject.toJSONString(autoConfigProperties));
        log.info("-----------------------------");
    }
}
