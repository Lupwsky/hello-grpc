package com.grpc.spring;

import com.grpc.spring.client.auto.config.*;
import com.grpc.spring.client.auto.config.annotation.GrpcClient;
import io.grpc.LoadBalancer;
import io.grpc.util.RoundRobinLoadBalancerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
        return new GrpcChannelsProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public GlobalClientInterceptorRegistry globalClientInterceptorRegistry() {
        return new GlobalClientInterceptorRegistry();
    }

    @Bean
    @ConditionalOnMissingBean
    public LoadBalancer.Factory grpcLoadBalancerFactory() {
        return RoundRobinLoadBalancerFactory.getInstance();
    }

    @Bean
    @ConditionalOnProperty(name="spring.cloud.etcd.disabled", havingValue="true")
    public GrpcChannelFactory addressChannelFactory(GrpcChannelsProperties channels, LoadBalancer.Factory loadBalancerFactory, GlobalClientInterceptorRegistry globalClientInterceptorRegistry) {
    	log.error("AddressChannelFactory----------------------");
        return new AddressChannelFactory(channels, loadBalancerFactory, globalClientInterceptorRegistry);
    }
   
//   @Configuration
//   @ConditionalOnBean(annotation=EnableEtcdDiscoveryClient.class)
//   @ConditionalOnProperty(name="spring.cloud.etcd.disabled",havingValue="false",matchIfMissing=true)
//   protected static class DiscoveryGrpcClientAutoConfiguration {
//
//       @ConditionalOnMissingBean
//       @Bean
//       public GrpcChannelFactory discoveryClientChannelFactory(GrpcChannelsProperties channels, DiscoveryClient discoveryClient, LoadBalancer.Factory loadBalancerFactory,
//                                                               GlobalClientInterceptorRegistry globalClientInterceptorRegistry) {
//    	   log.info("DiscoveryClientChannelFactory----------------------");
//           return new DiscoveryClientChannelFactory(channels, discoveryClient, loadBalancerFactory, globalClientInterceptorRegistry);
//       }
//   }

    @Bean
    @ConditionalOnClass(GrpcClient.class)
    @ConditionalOnMissingBean
    @Primary
    public GrpcClientBeanPostProcessor grpcClientBeanPostProcessor() {
        return new GrpcClientBeanPostProcessor();
    }

}
