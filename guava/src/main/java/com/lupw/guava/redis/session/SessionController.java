package com.lupw.guava.redis.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author v_pwlu 2019/2/20
 */
@Slf4j
@RestController
public class SessionController {

    @GetMapping("/spring/session/data/set/test")
    public ResponseEntity register(HttpServletRequest request) {
        request.getSession().setAttribute("data", "lupengwei");
        Map<String, Object> map = new HashMap<>();
        map.put("code", "0");
        map.put("message", "success");
        return new ResponseEntity(map, HttpStatus.OK);
    }


    @GetMapping("/spring/session/data/get/test")
    public ResponseEntity getSessionMessage(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("message",request.getSession().getAttribute("data")) ;
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
