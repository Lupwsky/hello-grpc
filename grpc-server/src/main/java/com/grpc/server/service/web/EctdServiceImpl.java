package com.grpc.server.service.web;

import lombok.extern.slf4j.Slf4j;
import mousio.etcd4j.EtcdClient;
import mousio.etcd4j.promises.EtcdResponsePromise;
import mousio.etcd4j.requests.EtcdKeyPutRequest;
import mousio.etcd4j.responses.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeoutException;

/**
 * Company: wesure
 * Project Name: hello-grpc
 * Description:
 *
 * @author v_pwlu
 * @date 2018/6/29
 */
@Slf4j
@Service
public class EctdServiceImpl {
    private static EtcdClient etcdClient;

    @PostConstruct
    public void init () {
        etcdClient = new EtcdClient(URI.create("http://127.0.0.1:2379"));
    }

    // 获取版本信息
    public EtcdVersionResponse getEtcdVersionInfo() {
        EtcdVersionResponse versionResponse = etcdClient.version();
        log.info(etcdClient.getVersion());
        log.info(versionResponse.getServer());
        log.info(versionResponse.getCluster());
        return versionResponse;
    }


    // put value
    public void putValue(String key, String value) {
        EtcdKeyPutRequest etcdKeyPutRequest = etcdClient.put(key, value);
        try {
            etcdKeyPutRequest.prevValue("lupw");
            etcdKeyPutRequest.prevExist(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info(etcdKeyPutRequest.getUri());
        try {
            EtcdResponsePromise<EtcdKeysResponse> etcdResponsePromise = etcdKeyPutRequest.send();
            EtcdKeysResponse response = etcdResponsePromise.get();
            log.info("key : " + response.node.key);
            log.info("value : " + response.node.value);
        } catch (IOException | EtcdException | TimeoutException | EtcdAuthenticationException e) {
            e.printStackTrace();
        }
    }
}
