package com.lupw.guava.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * @author lupw 2019-05-15
 */
@Slf4j
public class ReactorMain {

    // [Spring Reactor 入门与实践](https://www.jianshu.com/p/7ee89f70dfe5)
    // [使用 Reactor 进行反应式编程](https://www.ibm.com/developerworks/cn/java/j-cn-with-reactor-response-encode/index.html)
    // Reactor 也是 Spring 5 中反应式编程的基础, 学习和掌握 Reactor 可以更好地理解 Spring 5 中的相关概念
    // Flux 和 Mono 是 Reactor 中的两个基本概念
    // Flux 表示的是包含 0 到 N 个元素的异步序列。在该序列中可以包含三种不同类型的消息通知：正常的包含元素的消息、序列结束的消息和序列出错的消息
    // 当消息通知产生时，订阅者中对应的方法 onNext(), onComplete()和 onError()会被调用
    // Mono 表示的是包含 0 或者 1 个元素的异步序列, 该序列中同样可以包含与 Flux 相同的三种类型的消息通知
    // Flux 和 Mono 之间可以进行转换, 对一个 Flux 序列进行计数操作，得到的结果是一个 Mono<Long>对象
    // 把两个 Mono 序列合并在一起，得到的是一个 Flux 对象
    public static void main(String[] args) {
        // 创建 Flux.just()
        Flux<String> stringFlux1 = Flux.just("A", "B", "C");
        Flux<String> stringFlux2 = Flux.just("D", "E");
        Flux<String> stringFlux3 = Flux.concat(stringFlux1, stringFlux2);

        // 开始消费, 一个直接的方法就是调用 subscribe() 方法
        stringFlux1.map(s -> s + 1).subscribe(log::info);

    }
}
