package com.cris.springbootidempotent.service;

import org.springframework.stereotype.Service;

/**
 * 完成支付业务
 */
@Service
public class PayService {
    public boolean pay(String token) {
        try {
            //模拟支付过程
            Thread.sleep(1000);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
