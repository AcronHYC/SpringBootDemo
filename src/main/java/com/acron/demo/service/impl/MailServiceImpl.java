package com.acron.demo.service.impl;

import com.acron.demo.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Acron
 * @ClassName IMailServiceImpl
 * @Description TODO
 * @since 2019/06/29 22:20
 */
@Slf4j
@Service("mailService")
public class MailServiceImpl implements IMailService {
    @Resource
    private JavaMailSender mailSender;

    @Value("${mail.fromMail}")
    private String from;

    /*
     * @Description  发送邮件
     * @Param [to, subject, content]
     * @return void
     **/
    @Override
    public boolean sendSimpleMail(String to, String subject, String content){
        boolean flag=false;
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            log.info("邮件发送成功！");
            flag=true;
        } catch (Exception e) {
            log.error("发送邮件时发生异常!", e);
        }
        return flag;
    }

}
