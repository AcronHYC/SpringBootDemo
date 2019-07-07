package com.acron.demo.service.impl;

import com.acron.demo.service.ISMSService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;

/**
 * @author Acron
 * @ClassName SMSServiceImpl
 * @Description TODO
 * @since 2019/07/01 20:36
 */
@Slf4j
@Service("smsService")
public class SMSServiceImpl implements ISMSService {
    private static final String SMS_Url = "http://sms.webchinese.cn/web_api/";

    @Override
    public Integer send(String Uid,String Key,String sendPhoneNum,String desc){
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(SMS_Url);
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
        //设置参数
        NameValuePair[] data = {
                new NameValuePair("Uid", Uid),
                new NameValuePair("Key", Key),//秘钥
                new NameValuePair("smsMob", sendPhoneNum),
                new NameValuePair("smsText", desc)
        };
        post.setRequestBody(data);
        try {
            client.executeMethod(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        log.info("SMS_发送状态：{}",statusCode);
        String result ="";
        try {
            result = new String(post.getResponseBodyAsString().getBytes("gbk"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        post.releaseConnection();
        return Integer.parseInt(result);
    }

    /**
     *  -1  没有该用户账户
     -2 接口密钥不正确 [查看密钥]不是账户登陆密码
     -21    MD5接口密钥加密不正确
     -3 短信数量不足
     -11    该用户被禁用
     -14    短信内容出现非法字符
     -4 手机号格式不正确
     -41    手机号码为空
     -42    短信内容为空
     -51    短信签名格式不正确接口签名格式为：【签名内容】
     -6 IP限制
     大于0    短信发送数量
     以上作为补充
     */
    @Override
    public String getMessage(Integer code){
        String message;
        if(code > 0 ) {
            message = "SMS-f发送成功！";
        }else if(code == -1){
            message = "SMS-没有该用户账户";
        }else if(code == -2){
            message = "SMS-接口密钥不正确";
        }else if(code == -21){
            message = "SMS-MD5接口密钥加密不正确";
        }else if(code == -3){
            message = "SMS-短信数量不足";
        }else if(code == -11){
            message = "SMS-该用户被禁用";
        }else if(code == -14){
            message = "SMS-短信内容出现非法字符";
        }else if(code == -4){
            message = "SMS-手机号格式不正确";
        }else if(code == -41){
            message = "SMS-手机号码为空";
        }else if(code == -42){
            message = "SMS-短信内容为空";
        }else if(code == -51){
            message = "SMS-短信签名格式不正确接口签名格式为：【签名内容】";
        }else if(code == -6){
            message = "SMS-IP限制";
        }else{
            message = "其他错误";
        }
        return message;
    }
}
