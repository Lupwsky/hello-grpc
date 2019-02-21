package com.lupw.guava.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author v_pwlu 2019/2/20
 */
@Slf4j
@RestController
public class SessionTestController {

    @GetMapping(value = "/session/cookie/set/test")
    public HashMap<String, Object> sessionSetTest(HttpServletRequest request, HttpServletResponse response) {
        // Session 中的数据保存到 Cookie
        Cookie cookie = new Cookie("data", "lupengwei:");

        // 表示哪些请求路径可以获取到这个 Cookie,
        // 例如 JSESSIONID 这个 cookie 的 path 为 "/", 表示所有的同一个 domain 下的所有请求都可以获取到这个 cookie
        // 使用 cookie.setDomain() 方法可以设置 domain
        // 如果没有设置, 默认设置为当前请求的 BASE_URL, 如这个例子 path=/session/cookie/set;
        // 只有 domain + path + name 全匹配才能获取到这个 cookie
        // 因此在开发时需要考虑二级域名等问题
        cookie.setPath("/");
        // 过期时间
        cookie.setMaxAge(3600 * 1000 * 24);
        // 表示仅 HTTP 请求可以获取到这个 cookie
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        // 在浏览的 cookie 中看到类似如下数据
        // data=lupengwei;
        // path=/;
        // domain=localhost;
        // Expires=Wed, 20 Feb 2019 07:18:29 GMT;
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("sessionId", request.getSession(true).getId());
        return resultMap;
    }


    @GetMapping(value = "/session/cookie/get/test")
    public JSONObject sessionGetTest(HttpServletRequest request) {
        String cookieValue = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(Objects.equals(cookie.getName(), "data")) {
                cookieValue = cookie.getValue();
            }
        }
        JSONObject resultData = new JSONObject();
        resultData.put("data", cookieValue);
        return resultData;
    }
}
