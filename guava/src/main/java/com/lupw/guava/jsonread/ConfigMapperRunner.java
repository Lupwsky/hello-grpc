package com.lupw.guava.jsonread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author v_pwlu 2019/4/22
 */
@Component
public class ConfigMapperRunner implements CommandLineRunner {

    private final ConfigMapperServiceImpl configMapperService;

    @Autowired
    public ConfigMapperRunner(ConfigMapperServiceImpl configMapperService) {
        this.configMapperService = configMapperService;
    }

    @Override
    public void run(String... args) throws Exception {
        configMapperService.init();
    }
}
