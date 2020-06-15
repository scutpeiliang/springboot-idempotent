package com.cris.springbootidempotent.controller;

import com.cris.springbootidempotent.annotation.Idempotent;
import com.cris.springbootidempotent.service.PayService;
import com.cris.springbootidempotent.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OrderController {
    @Autowired
    private PayService payService;
    @Autowired
    private RedisUtils redisUtils;

    @Idempotent
    @RequestMapping("/pay")
    public String pay(HttpServletRequest request) {
        String token = StringUtils.isEmpty(request.getHeader("token"))? request.getParameter("token"): request.getHeader("token");
        boolean success = payService.pay(token);
        if (success) {
            return "支付成功！";
        } else {
            //支付失败,把token放回redis
            redisUtils.addKey(token);
            return "支付失败,请重试！";
        }
    }

}
