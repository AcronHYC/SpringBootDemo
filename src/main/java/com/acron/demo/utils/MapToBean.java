package com.acron.demo.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Acron
 * @ClassName MapToBean
 * @Description Map和bBean互相转换
 * @since 2019/07/27 12:03
 */
@Slf4j
public class MapToBean {
    /***
      * @Description:利用反射将map集合封装成bean对象
      * @Date: 2019/7/27
      * * @Param map: 被转化的Map
     * @Param clazz: 目标Bean
      * @return: T
      **/
    public static <T> T mapConvertToBean(Map<String, Object> map, Class<?> clazz) throws Exception{
        Object obj= clazz.newInstance();
        if(map != null && !map.isEmpty() && map.size()>0){
            map.forEach((key,value)->{
                String methodName="set"+key.substring(0,1).toUpperCase()+key.substring(1);
                Field field=getClassField(clazz,key);
                if(field==null){
                    return;
                }
                Class<?> fieldTypeClass=field.getType();
                Object fieldValue=convertValType(value,fieldTypeClass);
                try {
                    clazz.getMethod(methodName,field.getType()).invoke(obj,fieldValue);
                }catch (Exception e){
                    e.printStackTrace();
                    log.error("Map转换Bean发生异常:{}",e.getMessage());
                }
            });
        }
        return (T)obj;
    }

    /***
      * @Description:判断Bean中是否又匹配的字段
      * @Date: 2019/7/27
      * * @Param clazz:
     * @Param fieldName:
      * @return: java.lang.reflect.Field
      **/
    private static Field getClassField(Class<?> clazz,String fieldName){
        if(Object.class.getName().equals(clazz.getName())){
            return null;
        }
        Field[] fields=clazz.getDeclaredFields();
        for(Field field:fields){
            if(field.getName().equals(fieldName)){
                return field;
            }
        }
        Class<?> superClass=clazz.getSuperclass();
        if(superClass!=null){
            return getClassField(superClass,fieldName);
        }
        return null;
    }
    private static Object convertValType(Object value, Class<?> fieldTypeClass) {
        Object retVal = null;
        if (Long.class.getName().equals(fieldTypeClass.getName())
                || long.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Long.parseLong(value.toString());
        } else if (Integer.class.getName().equals(fieldTypeClass.getName())
                || int.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Integer.parseInt(value.toString());
        } else if (Float.class.getName().equals(fieldTypeClass.getName())
                || float.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Float.parseFloat(value.toString());
        } else if (Double.class.getName().equals(fieldTypeClass.getName())
                || double.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Double.parseDouble(value.toString());
        } else if(fieldTypeClass.getSuperclass().getName().equals("java.lang.Enum")){
            try {
                retVal=fieldTypeClass.getMethod("valueOf",String.class).invoke(fieldTypeClass,value);
            }catch (Exception e){
                log.error("枚举转换发生异常：{}",e.getMessage());
                e.printStackTrace();
            }
        } else {
            retVal = String.valueOf(value);
        }
        return retVal;
    }

    /***
      * @Description:Bean转化为Map
      * @Date: 2019/7/27
      * * @Param obj:
      * @return: java.util.Map<java.lang.String,java.lang.Object>
      **/
    public static Map<String, Object> objToMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();	// 获取f对象对应类中的所有属性域
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            try {
                boolean accessFlag = fields[i].isAccessible();	// 获取原来的访问控制权限
                fields[i].setAccessible(true);					// 修改访问控制权限
                Object o = fields[i].get(obj);					// 获取在对象f中属性fields[i]对应的对象中的变量
                if (o != null){
                    map.put(varName, o.toString());
                }
                fields[i].setAccessible(accessFlag);			// 恢复访问控制权限
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }
}
