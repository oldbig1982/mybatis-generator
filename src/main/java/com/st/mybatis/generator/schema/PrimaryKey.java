package com.st.mybatis.generator.schema;

import lombok.Data;

/**
 * @ClassName PrimaryKey
 * @Description 主键
 * @Author songtao
 * @Date 2020/4/28 0028 15:37
 **/
@Data
public class PrimaryKey {
    /**
     * 主键名称
     * primary key name (may be null)
     */
    private String pkName;
    /**
     * sequence number within primary key
     */
    private int keySeq;
    /**
     * 列名
     */
    private String columnName;
}
