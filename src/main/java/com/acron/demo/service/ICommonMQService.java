package com.acron.demo.service;

/**
 * @author Acron
 * @ClassName ICommonMQService
 * @Description TODO
 * @since 2019/07/07 15:00
 */
public interface ICommonMQService {
    /***
      * @Description:发送抢单消息
      * @Date: 2019/7/7
      * @Param mobile: 手机号码
      * @return: void
      **/
    void sendRobbingMsg(String mobile);

    /***
      * @Description:
      * @Date: 2019/7/7
      * @Param email: 目标邮箱
      * @return: void
      **/
    void sendEmailMsg(String email);
}
