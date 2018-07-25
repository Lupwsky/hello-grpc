package com.grpc.server.service.web;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.KV;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.kv.GetResponse;
import lombok.extern.slf4j.Slf4j;
//import mousio.etcd4j.EtcdClient;
//import mousio.etcd4j.promises.EtcdResponsePromise;
//import mousio.etcd4j.requests.EtcdKeyPutRequest;
//import mousio.etcd4j.responses.EtcdAuthenticationException;
//import mousio.etcd4j.responses.EtcdException;
//import mousio.etcd4j.responses.EtcdKeysResponse;
//import mousio.etcd4j.responses.EtcdVersionResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.net.URI;
//import java.util.concurrent.TimeoutException;

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
//    private static EtcdClient etcdClient;
//
//    @PostConstruct
//    public void init () {
//        etcdClient = new EtcdClient(URI.create("http://192.168.80.130:2379"),
//                URI.create("http://192.168.80.130:2380"),
//                URI.create("http://192.168.80.130:2381"));
//    }
//
//    // 获取版本信息
//    public EtcdVersionResponse getEtcdVersionInfo() {
//        EtcdVersionResponse versionResponse = etcdClient.version();
//        log.info(etcdClient.getVersion());
//        log.info(versionResponse.getServer());
//        log.info(versionResponse.getCluster());
//        return versionResponse;
//    }
//
//
//    // put value
//    public void putValue(String key, String value) {
//        EtcdKeyPutRequest etcdKeyPutRequest = etcdClient.put(key, value);
//        try {
//            etcdKeyPutRequest.prevValue("lupw");
//            etcdKeyPutRequest.prevExist(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        log.info(etcdKeyPutRequest.getUri());
//        try {
//            EtcdResponsePromise<EtcdKeysResponse> etcdResponsePromise = etcdKeyPutRequest.send();
//            EtcdKeysResponse response = etcdResponsePromise.get();
//            log.info("key : " + response.node.key);
//            log.info("value : " + response.node.value);
//        } catch (IOException | EtcdException | TimeoutException | EtcdAuthenticationException e) {
//            e.printStackTrace();
//        }
//    }


    // 获取版本信息
    public void getEtcdVersionInfo() {
        Client client = Client.builder().endpoints("http://192.168.80.130:2379").build();
        KV kvClient = client.getKVClient();
        try {
            ByteSequence bsKey = ByteSequence.fromString("username");
            ByteSequence bsValue = ByteSequence.fromString("lupw");
            kvClient.put(bsKey, bsValue).get();
            CompletableFuture<GetResponse> getFuture = kvClient.get(bsKey);
            GetResponse response = getFuture.get();
            log.warn("Response = {}", response.getKvs().get(0));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
