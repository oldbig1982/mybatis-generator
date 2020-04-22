package com.st.mybatis.generator.utils;

import com.st.mybatis.generator.schema.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * @ClassName DatabaseUtil
 * @Description 连接数据库，获取表结构
 * @Author songtao
 * @Date 2020/4/22 0022 18:14
 **/
public class DatabaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);

    private Connection connection = null;
    private String schema = null;
    Database database = null;

    private DatabaseUtil(){

    }

}
