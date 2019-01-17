package com.lupw.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author v_pwlu 2019/1/17
 */
@Slf4j
public class GuavaCacheMain {


    public static void main(String[] args) {
        cacheTest();
    }


    private static void cacheTest() {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                // 缓存大小
                .maximumSize(100)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        log.info("loadData = {}", s);
                        return s;
                    }
                });

        try {
            String cacheValue = cache.get("test");
            log.info("cacheValue = {}", cacheValue);

            cacheValue = cache.get("test");
            log.info("cacheValue = {}", cacheValue);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
