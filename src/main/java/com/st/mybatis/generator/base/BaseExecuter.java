package com.st.mybatis.generator.base;

import com.st.mybatis.generator.schema.Column;
import com.st.mybatis.generator.schema.Table;
import com.st.mybatis.generator.utils.TypeUtils;
import com.st.mybatis.generator.utils.WordsUtil;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @ClassName BaseExecuter
 * @Description JDBC sql转java
 * @Author songtao
 * @Date 2020/4/22 0022 17:02
 **/
public abstract class BaseExecuter {

    public abstract void build(Table table) throws IOException;


    protected static void buildClassComment(BufferedWriter bw, String prefix, String text) throws IOException {
        bw.newLine();
        bw.write("/**");
        bw.newLine();
        bw.write(" *");
        bw.newLine();
        if (prefix != null && prefix.trim().length() > 0) {
            bw.write(" * " + prefix);
            bw.newLine();
        }

        bw.write(" * " + text);
        bw.newLine();
        bw.write(" *");
        bw.newLine();
        bw.write(" */");
    }

    /**
     * 数据库字段类型转java数据类型
     * @param column
     * @return
     */
    protected static String processType(Column column) {
        String type = column.getType();
        return TypeUtils.processType(type);
    }

    /**
     * 字段名
     * @param field
     * @return
     */
    protected static String getInstanceName(String field) {
        return WordsUtil.getBeautyInstanceName(field);
    }
}
