package com.grpc.client.configuration.grpc;

import io.grpc.LoadBalancer;
import io.grpc.util.RoundRobinLoadBalancerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
@EnableConfigurationProperties
public class GrpcClientAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public GrpcChannelsProperties grpcChannelsProperties() {
        log.info("GrpcChannelsProperties bean create");
        return new GrpcChannelsProperties();
    }


    @Bean
    @ConditionalOnMissingBean
    public LoadBalancer.Factory grpcLoadBalancerFactory() {
        log.info("RoundRobinLoadBalancerFactory bean create");
        return RoundRobinLoadBalancerFactory.getInstance();
    }


    @Bean
    @ConditionalOnMissingBean
    public GlobalClientInterceptorRegistry globalClientInterceptorRegistry() {
        return new GlobalClientInterceptorRegistry();
    }


    @Bean
    @ConditionalOnMissingBean
    public GrpcChannelFactory addressChannelFactory(GrpcChannelsProperties grpcChannelsProperties,
                                                    LoadBalancer.Factory loadBalancerFactory,
                                                    GlobalClientInterceptorRegistry globalClientInterceptorRegistry) {
        return new AddressChannelFactory(grpcChannelsProperties, loadBalancerFactory, globalClientInterceptorRegistry);
    }


    @Bean
    @Primary
    @ConditionalOnMissingBean
    public GrpcClientBeanPostProcessor grpcClientBeanPostProcessor() {
        return new GrpcClientBeanPostProcessor();
    }

}
