package com.st.mybatis.generator.schema;

import lombok.Data;

/**
 * @ClassName Column
 * @Description 列
 * @Author songtao
 * @Date 2020/4/22 0022 15:44
 **/
@Data
public class Column {
    /**
     * 列名
     */
    private String name;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 长度
     */
    private int size;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 注释
     */
    private String comment;
    /**
     * 非空约束
     */
    private boolean nullable;
}
