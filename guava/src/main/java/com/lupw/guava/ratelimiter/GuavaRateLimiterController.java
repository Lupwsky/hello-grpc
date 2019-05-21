package com.lupw.guava.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author v_pwlu 2019/5/21
 */
@Slf4j
@RestController
public class GuavaRateLimiterController {

    private final RateLimiter rateLimiter;

    @Autowired
    public GuavaRateLimiterController(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }


    @GetMapping("/guava/rate/limiter/test1")
    public void beginTest1() {
        test1();
    }

    private void test1() {
        if (rateLimiter.tryAcquire(1)) {
            log.info("尝试获取令牌成功");
            log.info("获取令牌 = {}", rateLimiter.acquire());
        } else {
            log.info("尝试获取令牌失败");
        }
    }

}
