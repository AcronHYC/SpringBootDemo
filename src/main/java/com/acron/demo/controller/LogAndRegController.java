package com.acron.demo.controller;

import com.acron.demo.service.ICommonMQService;
import com.acron.demo.service.IMailService;
import com.acron.demo.service.ISMSService;
import com.acron.demo.thread.InitThread;
import com.acron.demo.utils.Constants;
import com.acron.demo.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import java.util.concurrent.TimeUnit;

/**
 * @author Acron
 * @ClassName LogAndRegController
 * @Description TODO
 * @since 2019/06/30 11:31
 */
@Api(value = "LogAndReg-Controller",description = "登录注册接口测试")
@Slf4j
@RestController
@RequestMapping("/LogAndReg")
public class LogAndRegController {
    @Resource
    private IMailService mailService;

    @Resource
    private ISMSService smsService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ICommonMQService commonMQService;

    @Resource
    private InitThread initThread;

    @ApiOperation(value = "根据邮箱获取验证码",notes = "根据邮箱获取验证码")
    @GetMapping(value = "/emailVerificationCode/{email}")
    public boolean getEmailVerificationCode(@ApiParam("邮箱") @PathVariable @Email(message = "邮箱格式不正确!") String email){
        String verificationCode= Utils.generateNumber();
        boolean flag= mailService.sendSimpleMail(email, Constants.SUBJECT,Constants.EMAIL_VERIFICATION_CODE+verificationCode);
        if(flag){
            stringRedisTemplate.opsForValue().set("User_EmailVerificationCode:"+email,verificationCode,3, TimeUnit.MINUTES);
            log.info("邮件发送成功!");
        }
        return flag;
    }

    @ApiOperation(value = "获取手机验证码",notes = "获取手机验证码")
    @GetMapping(value = "/phoneVerificationCode/{phone}")
    public String getPhoneVerificationCode(@ApiParam("手机号码") @PathVariable String phone){
        String verificationCode= Utils.generateNumber();
        int statusCode=smsService.send("Acron","d41d8cd98f00b204e980",phone,"验证码:"+verificationCode+"（3分钟内有效）");
        if(statusCode>0){
            stringRedisTemplate.opsForValue().set("User_PhoneVerificationCode:"+phone,verificationCode,3, TimeUnit.MINUTES);
            log.info("手机验证码发送成功!");
        }
        return smsService.getMessage(statusCode);
    }

    @ApiOperation(value = "邮件发送队列",notes = "邮件发送队列")
    @GetMapping(value = "mail/{mail}")
    public void sendMail(@ApiParam("邮箱") @PathVariable @Email(message = "邮箱格式不正确!") String mail) {
        try {
            commonMQService.sendEmailMsg(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
