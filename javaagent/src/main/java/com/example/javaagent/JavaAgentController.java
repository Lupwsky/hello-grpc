package com.example.javaagent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author v_pwlu 2019/5/27
 */
@Slf4j
@RestController
public class JavaAgentController {

    @GetMapping("/java/agent/test")
    public void javaAgentTest() {
        log.info("java agent test...");
    }
}
