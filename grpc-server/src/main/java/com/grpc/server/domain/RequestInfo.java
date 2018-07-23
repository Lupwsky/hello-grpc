package com.grpc.server.domain;

import lombok.Data;

/**
 * company: wesure
 * project Name: hello-grpc
 *
 * @author v_pwlu 2018/7/20
 */
@Data
public class RequestInfo {
    private String id;
    private RequestItem item;
}
