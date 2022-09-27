package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.SmsService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Davy
 */
@RestController
@RequestMapping("/smsservice/sms")
@CrossOrigin
public class SmsApiController {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private SmsService smsService;

    //阿里云发送短信
    @GetMapping("sendMsgByAly/{phone}")
    public R sendMsgByAly(@PathVariable String phone){
        //生成随机值,传递到阿里云进行发送
        String code = RandomUtil.getSixBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        //调用service进行短信发送
        boolean isSend = smsService.sendMsgAly(param, phone);
        if(isSend){
            return R.ok();
        }else{
            return R.error().message("短信发送失败");
        }
    }

    //使用国阳云发送短信   已过期
    @GetMapping("sendMsgByGyy/{phone}")
    public R sendMsgByGyy(@PathVariable String phone){
        //从redis中获取验证码,如果获取则直接返回..==> 根据现实情况,用户可以多次发送.
         String code = redisTemplate.opsForValue().get(phone);
         if(!StringUtils.isEmpty(code)){
             //调用service进行短信发送
             boolean isSend = smsService.sendMsgGyy(code, phone);
             if(isSend){
                 return R.ok();
             }
         }
        //2.如果redis获取不到,进行阿里云发送

        //生成随机值,传递到阿里云进行发送
        code = RandomUtil.getSixBitRandom();
        //调用service进行短信发送
        boolean isSend = smsService.sendMsgGyy(code, phone);
        if(isSend){
            //发送成功,把发送成功验证码放入到redis中
            //设置有效时间
            redisTemplate.opsForValue().set(phone, code, 30, TimeUnit.MINUTES);
            return R.ok();
        }else{
            return R.error().message("短信发送失败");
        }
    }
}
