package com.st.mybatis.generator.schema;

import lombok.Data;

/**
 * @ClassName Database
 * @Description 数据库对象
 * @Author songtao
 * @Date 2020/4/22 0022 15:48
 **/
@Data
public class Database {
    /**
     * 数据库名
     */
    private String productName;
    /**
     * 数据库版本
     */
    private String productVersion;
}
