package com.acron.demo.service;

/**
 * @author Acron
 * @ClassName IMailService
 * @Description TODO
 * @since 2019/06/29 22:18
 */
public interface IMailService {
    /**
     * @Description 邮件服务
     * @Param [目标邮箱, 主题, 内容]
     * @return boolean
     **/
    boolean sendSimpleMail(String to, String subject, String content);
}
