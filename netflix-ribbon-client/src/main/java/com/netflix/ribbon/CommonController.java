package com.netflix.ribbon;

import com.netflix.client.ClientFactory;
import com.netflix.config.ConfigurationManager;
import com.netflix.niws.client.http.RestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/7/30
 */
@RestController
public class CommonController {

    @GetMapping(value = "/ribbon/client/get/request/url")
    public void test() {
        ConfigurationManager.getConfigInstance().setProperty( "my-client.ribbon.listOfServers", "localhost:9041, localhost:9042");
        RestClient  client = (RestClient) ClientFactory.getNamedClient("my-client");

    }

}
