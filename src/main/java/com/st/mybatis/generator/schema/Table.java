package com.st.mybatis.generator.schema;

import lombok.Data;

import java.util.List;

/**
 * @ClassName Table
 * @Description 表
 * @Author songtao
 * @Date 2020/4/22 0022 15:46
 **/
@Data
public class Table {
    /**
     * 表名
     */
    private String name;
    /**
     * 注释
     */
    private String comment;
    /**
     * 列
     */
    private List<Column> columns;

    /**
     * 主键
     */
    private List<PrimaryKey> primaryKeys;
}
