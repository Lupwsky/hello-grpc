package com.grpc.client;

import com.grpc.client.service.web.RestTemplateServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/7/19
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RestTemplateServiceImplTest {

    @Autowired
    private RestTemplateServiceImpl restTemplateService;


    @Test
    public void restTemplateGetRequestTest() {
        restTemplateService.restTemplateGetRequest();
    }
}
