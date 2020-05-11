package com.st.mybatis.generator.utils;

/**
 * @ClassName WordsUtil
 * @Description 表名、字段名、类名
 * @Author songtao
 * @Date 2020/4/30 0030 16:51
 **/
public class WordsUtil {

    /**
     * 表名转类名
     * @param name
     * @return
     */
    public static String getBeautyObjectName(String name) {
        if (name != null && !name.isEmpty()) {
            String key = name.toLowerCase();
            key = key.replace("_", "");
            String beautyName = null;
            if (name.contains("_")) {
                StringBuilder sb = new StringBuilder();
                String[] fields = name.split("_");
                sb.append(fields[0].toLowerCase());

                for(int i = 1; i < fields.length; ++i) {
                    String temp = fields[i];
                    sb.append(temp.substring(0, 1).toUpperCase());
                    sb.append(temp.substring(1).toLowerCase());
                }

                beautyName = sb.toString();
            } else if (!name.toLowerCase().equals(name) && !name.toUpperCase().equals(name)) {
                beautyName = name;
            } else {
                beautyName = name.toLowerCase();
            }

            beautyName = upperCaseFirstChar(beautyName);
            return beautyName;
        } else {
            throw new IllegalArgumentException("name must have value.");
        }
    }


    /**
     * 首字母大写
     * @param name
     * @return
     */
    public static String upperCaseFirstChar(String name){
        return name;
    }


    /**
     * 数据库字段转自定义java字段。
     * @param field
     * @return
     */
    public static String getBeautyInstanceName(String field){
        return field;
    }
}
