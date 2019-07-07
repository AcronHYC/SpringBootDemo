package com.acron.demo.service;

/**
 * @author Acron
 * @ClassName ISMSService
 * @Description TODO
 * @since 2019/07/01 20:19
 */
public interface ISMSService {
    /**
     *
     * @param Uid SMS用户id：
     * @param Key 接口秘钥：SMS登录可查（非登录密码）
     * @param sendPhoneNum 短信发送目标号码
     * @param desc 短信内容
     * @return Integer(1：成功码，其他失败，具体参见注释)
     */
    Integer send(String Uid,String Key,String sendPhoneNum,String desc);

    /**
     *
     * @param code 状态码
     * @return 发送状态
     */
    String getMessage(Integer code);
}
