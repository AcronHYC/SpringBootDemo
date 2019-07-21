package com.acron.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Acron
 * @ClassName Utils
 * @Description TODO
 * @since 2019/06/27 20:33
 */
public class Utils {
    public static String formatDate(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String generateNumber(){
        return String.valueOf((int)((Math.random()*9+1)*100000));
    }
}
