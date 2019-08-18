package com.acron.demo.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Acron
 * @ClassName ResponseUtil
 * @Description TODO
 * @since 2019/08/02 22:15
 */
@Slf4j
public class ResponseUtil {
    /**
     *  使用response输出JSON
     * @param
     * @param
     */
    /*public static void out(ServletResponse response, Map<String, Object> resultMap){
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(resultMap));
        } catch (Exception e) {
            log.error(e + "输出JSON出错");
        }finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }
    }*/

    public static Map<String, Object> resultMap(Integer code, String msg){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("message", msg);
        resultMap.put("status", code);
        return resultMap;
    }

    public static Map<String, Object> resultMap( Integer status, String msg, Object data){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("message", msg);
        resultMap.put("status", status);
        resultMap.put("data", data);
        return resultMap;
    }
}
