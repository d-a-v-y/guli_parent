package com.atguigu.msmservice.service;

import java.util.Map;

/**
 * @author Davy
 */
public interface SmsService {
    boolean sendMsgAly(Map<String, Object> param, String phone);

    boolean sendMsgGyy(String code, String phone);
}
