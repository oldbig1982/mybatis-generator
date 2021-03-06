package com.st.mybatis.generator.utils;

import com.st.mybatis.generator.schema.Column;
import com.st.mybatis.generator.schema.Table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName GeneratorUtils
 * @Description
 * @Author songtao
 * @Date 2020/5/2 0007 13:28
 **/
public class GeneratorUtils {

    /**
     * 表名对应类名
     * @param tableName
     * @return
     */
    public static String getObjectName(String tableName){

        return tableName;
    }

    /**
     * types中是否存在日期类型
     * @param types
     * @return
     */
    public static boolean haveDate(List<String> types) {
        if (types != null && !types.isEmpty()){
            for (String type : types) {
                if (type.toLowerCase().contains("date")
                        || type.toLowerCase().contains("time")){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * types中是否存在Bigdecimal类型
     * @param types
     * @return
     */
    public static boolean haveDecimal(List<String> types) {
        if (types != null && !types.isEmpty()){
            for (String type : types) {
                if (type.toLowerCase().contains("number")
                        || type.toLowerCase().contains("numeric")
                        || type.toLowerCase().contains("decimal")){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 取出表中字段的类型
     * @param table
     * @return
     */
    public static List<String> getTableColumnTypes(Table table) {
        List<String> types = new ArrayList();
        List<Column> columns = table.getColumns();
        int size = columns.size();

        for(int i = 0; i < size; ++i) {
            Column column = (Column)columns.get(i);
            types.add(column.getType());
        }
        return types;
    }
}
