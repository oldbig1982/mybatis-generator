package com.st.mybatis.generator.utils;

/**
 * @ClassName TypeUtils
 * @Description 数据库字段类型转java类型
 * @Author songtao
 * @Date 2020/5/11 0011 17:25
 **/
public class TypeUtils {
    public static String processType(String type) {
        String result = "";
        if (type.endsWith(" identity")) {
            type = type.substring(0, type.length() - " identity".length());
        }

        String lowerType = type.toUpperCase();
        switch(lowerType) {
            case "VARCHAR":
                result="String";
                break;

        }


        return result;
    }
}
