DelayQueue 可以用来实现延迟消息, 如果不是在分布式系统中, 一个简单的延时消息队列可以使用 DelayQueue 来实现, 在分布式系统下, 就需要其他的方法来实现了, 在分布式系统种目前使用的多的就是 MQ 和 Redis 来实现了, 这里记录 Redis 的实现

# Redis 延时队列的实现

延时队列可以通过 Redis 的有序结合来实现, score 为消息的过期时间, value 为序列化成字符串的消息, 消息添加到队列后, 使用 zrangebyscore 获取数据判断是否有数据有过期, 有数据说明有消息过期了, 然后调用 zrem 删除该消息并对该消息进行处理, 实现如下:

```java
public void redisDelayQueueTest() {
    StatefulRedisConnection<String, String> connection = redisClient.connect();
    RedisCommands<String, String> redisCommands = connection.sync();
    
    String key = "delay_queue";
    // 实际开发建议使用业务 ID 和随机生成的唯一 ID 作为 value, 随机生成的唯一 ID 可以保证消息的唯一性, 业务 ID 可以避免 value 携带的信息过多
    String value1 = JSONObject.toJSONString(UserInfo.builder().username("lpw1").password("123").build());
    String value2 = JSONObject.toJSONString(UserInfo.builder().username("lpw2").password("123").build());
    String value3 = JSONObject.toJSONString(UserInfo.builder().username("lpw3").password("123").build());
    redisCommands.zadd(key, ScoredValue.from(DateTime.now().getMillis() + 1000 * 10, Optional.ofNullable(value1)));
    redisCommands.zadd(key, ScoredValue.from(DateTime.now().getMillis() + 1000 * 15, Optional.ofNullable(value2)));
    redisCommands.zadd(key, ScoredValue.from(DateTime.now().getMillis() + 1000 * 20, Optional.ofNullable(value3)));
    
    new Thread() {
        @Override
        public void run() {
            while (true) {
                List<String> resultList;
                // 只获取第一条数据, 只获取不会移除数据
                resultList = redisCommands.zrangebyscore(key, Range.create(0, DateTime.now().getMillis()), Limit.create(0, 1));
                if (resultList.size() == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                } else {
                    // 移除数据获取到的数据
                    if (redisCommands.zrem(key, resultList.get(0)) > 0) {
                        log.info("userInfo = {}", resultList.get(0));
                    }
                }
            }
        }
    }.start();
}
```

上面的实现, 在多线程逻辑上也是没有问题的, 假设有两个线程 T1, T2和其他更多线程, 处理逻辑如下, 保证了多线程情况下只有一个线程处理了对应的消息:

* T1, T2 和其他更多线程调用 zrangebyscore 获取到了一条消息 A
* T1 准备开始删除消息 A, 由于是原子操作, T2 和其他更多线程等待 T1 执行 zrem 删除消息 A 后再执行 zrem 删除消息 A
* T1 删除了消息 A, 返回删除成功标记 1, 并对消息 A 进行处理
* T2 其他更多线程开始 zrem 删除消息 A, 由于消息 A 已经被删除, 所以所有的删除均失败, 放弃了对消息 A 的处理

使用 Redis 实现的延时消息队列也存在数据持久化, 消息可靠性的问题

1. 没有重试机制 - 处理消息出现异常没有重试机制, 这些需要自己去实现, 包括重试次数的实现等
2. 没有 ACK 机制 - 例如在获取消息并已经删除了消息情况下, 正在处理消息的时候客户端崩溃了, 这条正在处理的这些消息就会丢失, MQ 是需要明确的返回一个值给 MQ 才会认为这个消息是被正确的消费了

# 使用调用 Lua 脚本实现



# 参考资料

* [Redis 深度历险：核心原理与应用实践](https://juejin.im/book/5afc2e5f6fb9a07a9b362527/section/5afc3643518825672034404b)
* [Redis Lua 脚本调试器用法说明](http://blog.huangz.me/2017/redis-lua-debuger-introduction.html)