package com.grpc.client.configuration.grpc;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.grpc.*;
import io.grpc.internal.SharedResourceHolder;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.GuardedBy;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * User: Michael
 * Email: yidongnan@gmail.com
 * Date: 5/17/16
 */
@Slf4j
public class AddressChannelNameResolver extends NameResolver {

    private final String name;
    private final GrpcChannelProperties properties;
    private final Attributes attributes;

    private final SharedResourceHolder.Resource<ExecutorService> executorResource;

    /**
     * 任务调度器
     */
    @GuardedBy("this")
    private ExecutorService executor;

    /**
     * 监听器，更新地址
     */
    @GuardedBy("this")
    private Listener listener;

    /**
     * 解析器是否已经退出
     */
    @GuardedBy("this")
    private boolean shutdown;

    /**
     * 是否正在解析 gRPC 服务器地址
     */
    @GuardedBy("this")
    private boolean resolving;


    /**
     * 域名解析器构造器
     *
     * @param name 解析器名称
     * @param properties 配置文件中配置的 RPC 服务器的信息
     * @param attributes metadata 数据
     * @param executorResource 一个 gRPC 实现的任务调度器
     */
    public AddressChannelNameResolver(String name,
                                      GrpcChannelProperties properties,
                                      Attributes attributes,
                                      SharedResourceHolder.Resource<ExecutorService> executorResource) {
        this.name = name;
        this.properties = properties;
        this.attributes = attributes;
        this.executorResource = executorResource;
    }


    /**
     * 获取解析器的名称
     *
     * @return 解析器名称
     */
    @Override
    public String getServiceAuthority() {
        return name;
    }


    /**
     * 开始解析
     *
     * @param listener 地址更新的监听器
     */
    @Override
    public final synchronized void start(Listener listener) {
        Preconditions.checkState(this.listener == null, "already started");
        this.listener = listener;
        executor = SharedResourceHolder.get(executorResource);
        this.listener = Preconditions.checkNotNull(listener, "listener");
        resolve();
    }


    @Override
    public final synchronized void refresh() {
        Preconditions.checkState(listener != null, "not started");
        resolve();
    }


    private final Runnable resolutionRunnable = new Runnable() {
        @Override
        public void run() {
            Listener savedListener;
            synchronized (AddressChannelNameResolver.this) {
                if (shutdown) {
                    return;
                }
                savedListener = listener;
                resolving = true;
            }

            try {
                // 简单通过比较 host 和 post 的大小是否一致检测配置文件中的 gRPC 服务器中的配置是否错误
                if (properties.getHost().size() != properties.getPort().size()) {
                    savedListener.onError(Status.UNAVAILABLE.withCause(new RuntimeException("gRPC 配置错误")));
                    return;
                }

                // 解析并添加 IP
                List<EquivalentAddressGroup> equivalentAddressGroupList = Lists.newArrayList();
                for (int i = 0; i < properties.getHost().size(); i++) {
                    String host = properties.getHost().get(i);
                    Integer port = properties.getPort().get(i);
                    log.debug("发现 gRPC 服务 {} {}:{}", name, host, port);
                    equivalentAddressGroupList.add(new EquivalentAddressGroup(new InetSocketAddress(host, port), Attributes.EMPTY));
                }

                // 更新 IP
                savedListener.onAddresses(equivalentAddressGroupList, Attributes.EMPTY);
            } finally {
                synchronized (AddressChannelNameResolver.this) {
                    resolving = false;
                }
            }
        }
    };


    /**
     * 解析地址
     *
     */
    @GuardedBy("this")
    private void resolve() {
        if (resolving || shutdown) {
            return;
        }
        executor.execute(resolutionRunnable);
    }


    /**
     * 关闭地址解析器
     *
     */
    @Override
    public void shutdown() {
        if (shutdown) {
            return;
        }
        shutdown = true;
        if (executor != null) {
            executor = SharedResourceHolder.release(executorResource, executor);
        }
    }
}
