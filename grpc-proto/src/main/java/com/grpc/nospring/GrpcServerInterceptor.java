package com.grpc.nospring;

import com.hello.grpc.proto.Response;
import io.grpc.*;

/**
 * Company: wesure
 * Project Name: hello-grpc
 * Description:
 *
 * @author v_pwlu
 * @date 2018/6/21
 */
public class GrpcServerInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {


        return serverCallHandler.startCall(new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(serverCall) {
            @Override
            public void sendHeaders(Metadata headers) {
                Metadata.Key<String> TEST = Metadata.Key.of("test", Metadata.ASCII_STRING_MARSHALLER);
                headers.put(TEST, "TEST");
                super.sendHeaders(headers);
            }

            @Override
            public void sendMessage(RespT message) {
                Response response = (Response) message;

                if (response.getName().equals("test")) {
                    response.newBuilderForType().setName("TEST").build();
                }

                super.sendMessage(message);
            }
        }, metadata);
    }
}
