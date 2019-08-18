package com.acron.demo.controller;

import com.acron.demo.core.base.entity.BaseResult;
import com.acron.demo.entity.database.User;
import com.acron.demo.service.ICommonMQService;
import com.acron.demo.service.IMailService;
import com.acron.demo.service.ISMSService;
import com.acron.demo.service.IUserService;
import com.acron.demo.thread.InitThread;
import com.acron.demo.utils.Constants;
import com.acron.demo.utils.MapToBean;
import com.acron.demo.utils.Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;
import java.io.IOException;
import java.util.Map;
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

    @Resource
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /* 重定向策略 */
    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();

    /* 将当前请求缓存到SESSION */
    private RequestCache requestCache=new HttpSessionRequestCache();

    private SecurityProperties securityProperties;

    @ApiOperation(value = "根据邮箱获取验证码",notes = "根据邮箱获取验证码")
    @GetMapping(value = "/emailVerificationCode/{email}")
    public BaseResult getEmailVerificationCode(@ApiParam("邮箱") @PathVariable @Email(message = "邮箱格式不正确!") String email){
        String verificationCode= Utils.generateNumber();
        boolean flag= mailService.sendSimpleMail(email, Constants.SUBJECT,Constants.EMAIL_VERIFICATION_CODE+verificationCode);
        if(flag){
            stringRedisTemplate.opsForValue().set("User_EmailVerificationCode:"+email,verificationCode,5, TimeUnit.MINUTES);
            log.info("邮件发送成功!");
            return BaseResult.success(true);
        }
        return BaseResult.fail(-1,"发送邮件失败！",flag);
    }

    @ApiOperation(value = "获取手机验证码",notes = "获取手机验证码")
    @GetMapping(value = "/phoneVerificationCode/{phone}")
    public String getPhoneVerificationCode(@ApiParam("手机号码") @PathVariable String phone){
        String verificationCode= Utils.generateNumber();
        int statusCode=smsService.send("Acron","d41d8cd98f00b204e980",phone,"验证码:"+verificationCode+"（3分钟内有效）");
        if(statusCode>0){
            stringRedisTemplate.opsForValue().set("User_PhoneVerificationCode:"+phone,verificationCode,10, TimeUnit.MINUTES);
            log.info("手机验证码发送成功!");
        }
        return smsService.getMessage(statusCode);
    }

    @ApiOperation(value = "邮件发送队列",notes = "邮件发送队列")
    @GetMapping(value = "/mail/{mail}")
    public void sendMail(@ApiParam("邮箱") @PathVariable @Email(message = "邮箱格式不正确!") String mail) {
        try {
            commonMQService.sendEmailMsg(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "用户登录",notes = "用户登录")
    @PostMapping(value = "/login",consumes = "application/json;charset=UTF-8")
    public void login(@RequestBody Map<String,String> queryParams){
        String userName=queryParams.get("userName");
        String password=queryParams.get("password");
        if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)){
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,password));
        }
    }

    @ApiOperation(value = "创建用户",notes = "创建用户")
    @PostMapping(value = "/",consumes = "application/json;charset=UTF-8")
    public BaseResult createUser(@ApiParam("创建对象") @RequestBody Map<String,Object> params) throws Exception{
        /*String verificationCode=stringRedisTemplate.opsForValue().get("User_EmailVerificationCode:"+params.get("email"));
        if(StringUtils.isEmpty(verificationCode)){
            return BaseResult.fail(-1,"验证码已过期！");
        }else if(!verificationCode.equals(params.get("confirmEmail"))){
            return BaseResult.fail(-1,"验证码不正确！");
        }*/
        User user= MapToBean.mapConvertToBean(params,User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        boolean flag=userService.save(user);
        if(flag){
            stringRedisTemplate.delete("User_EmailVerificationCode:"+params.get("email"));
            return BaseResult.success(true);
        }
        log.info("新建用户成功!");
        return BaseResult.fail(-1,"创建用户发生错误!");
    }

    @GetMapping("/logout")
    public void logout(){ }
}
