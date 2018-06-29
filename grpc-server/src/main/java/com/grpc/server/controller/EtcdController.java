package com.grpc.server.controller;

import com.grpc.server.service.web.EctdServiceImpl;
import mousio.etcd4j.responses.EtcdVersionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Company: wesure
 * Project Name: hello-grpc
 * Description:
 *
 * @author v_pwlu
 * @date 2018/6/29
 */
@RestController
public class EtcdController {
    private final EctdServiceImpl ectdService;


    @Autowired
    public EtcdController(EctdServiceImpl ectdService) {
        this.ectdService = ectdService;
    }


    @RequestMapping(value = "/etcd/get/version/info", method = RequestMethod.GET)
    public Map<String, Object> getEctdVersionInfo () {
        Map<String, Object> resp = new HashMap<>();
        EtcdVersionResponse versionResponse = ectdService.getEtcdVersionInfo();
        resp.put("server", versionResponse.getServer());
        resp.put("cluster", versionResponse.getCluster());
        return resp;
    }



    @RequestMapping(value = "/etcd/put/value", method = RequestMethod.POST)
    public void putValue (String key, String value) {
        ectdService.putValue(key, value);
    }
}
