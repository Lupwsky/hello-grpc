package com.grpc.client.configuration.grpc;

import io.grpc.LoadBalancer;
import io.grpc.util.RoundRobinLoadBalancerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
@EnableConfigurationProperties
@ConditionalOnClass({GrpcChannelFactory.class})
public class GrpcClientAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public GrpcChannelsProperties grpcChannelsProperties() {
        log.error("GrpcChannelsProperties bean create");
        return new GrpcChannelsProperties();
    }


    @Bean
    @ConditionalOnMissingBean
    public LoadBalancer.Factory grpcLoadBalancerFactory() {
        log.error("RoundRobinLoadBalancerFactory bean create");
        // 客户端负载均衡策略
        return RoundRobinLoadBalancerFactory.getInstance();
    }


    @Bean
    @ConditionalOnMissingBean
    public GlobalClientInterceptorRegistry globalClientInterceptorRegistry() {
        log.error("GlobalClientInterceptorRegistry bean create");
        return new GlobalClientInterceptorRegistry();
    }


    @Bean
    @ConditionalOnMissingBean
    public GrpcChannelFactory addressChannelFactory(GrpcChannelsProperties grpcChannelsProperties,
                                                    LoadBalancer.Factory loadBalancerFactory,
                                                    GlobalClientInterceptorRegistry globalClientInterceptorRegistry) {
    	log.error("AddressChannelFactory bean create");
        return new AddressChannelFactory(grpcChannelsProperties, loadBalancerFactory, globalClientInterceptorRegistry);
    }


    @Bean
    @Primary
    @ConditionalOnMissingBean
    public GrpcClientBeanPostProcessor grpcClientBeanPostProcessor() {
        log.error("GrpcClientBeanPostProcessor bean create");
        return new GrpcClientBeanPostProcessor();
    }

}
