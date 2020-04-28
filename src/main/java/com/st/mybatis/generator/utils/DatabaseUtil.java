package com.st.mybatis.generator.utils;

import com.st.mybatis.generator.schema.Column;
import com.st.mybatis.generator.schema.Database;
import com.st.mybatis.generator.schema.PrimaryKey;
import com.st.mybatis.generator.schema.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DatabaseUtil
 * @Description 连接数据库，获取表结构
 * @Author songtao
 * @Date 2020/4/22 0022 18:14
 **/
public class DatabaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);

    private Connection connection;
    /**
     * 表所在的模式。
     * "“”"表示没有任何模式，
     * Null表示所有模式。
     */
    private String schema;

    private Database database;

    private DatabaseUtil(){
    }

    /**
     *获取数据库信息
     * @param connection
     * @param schema
     * @return
     */
    public static DatabaseUtil getInstance(Connection connection, String schema){
        DatabaseUtil databaseUtil = new DatabaseUtil();
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            Database database = new Database();
            database.setProductName(dbmd.getDatabaseProductName());
            database.setProductVersion(dbmd.getDatabaseProductVersion());
            databaseUtil.database = database;
            databaseUtil.connection = connection;
            databaseUtil.schema = schema;
        }catch (Exception e){
            logger.error("{}",e);
        }

        return databaseUtil;
    }

    /**
     * 获取所有表名，键是小写
     * @return
     */
    public Map<String,String> getAllTableNamesMap(){
        HashMap<String, String> tableMap = new HashMap<>();
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, schema, null, new String[]{"TABLE"});
            while (resultSet.next()){
                String tableName = resultSet.getString("TABLE_NAME");
                tableMap.put(tableName.toLowerCase(),tableName);
            }
            resultSet.close();
        }catch (SQLException e){
            logger.error("{}",e);
        }
        return tableMap;
    }

    /**
     * 获取表信息
     * @param tableName
     * @return
     */
    public Table getTableInfo(String tableName){
        Table table = new Table();
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet rs = dbmd.getTables(null,this.schema,tableName,new String[]{"TABLE"});
            if (rs.next()){
                String comment = rs.getString("REMARKS");
                if (comment != null){
                    table.setComment(comment);
                }
            }
            rs.close();
        }catch (SQLException e){
            logger.error("{}",e);
        }
        table.setColumns(getTableCloumns(tableName));
        table.setPrimaryKeys(getTablePrimaryKeys(tableName));
        return table;
    }


    /**
     * 获取表的列
     * @param tableName
     * @return
     */
    private List<Column> getTableCloumns(String tableName){
        List<Column> columns = new ArrayList<>();
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet rs = dbmd.getColumns(null,schema,tableName,null);
            while (rs.next()){
                Column column = new Column();
                column.setName(rs.getString("COLUMN_NAME"));
                column.setType(rs.getString("TYPE_NAME"));
                column.setSize(rs.getInt("COLUMN_SIZE"));
                column.setComment(rs.getString("REMARKS"));
                column.setNullable(rs.getBoolean("NULLABLE"));
                column.setDefaultValue(rs.getString("COLUMN_DEF"));
                columns.add(column);
            }
            rs.close();
        }catch (SQLException e){
            logger.error("{}",e);
        }
        return columns;
    }


    /**
     * 获取表的主键
     * @param tableName
     * @return
     */
    private List<PrimaryKey> getTablePrimaryKeys(String tableName) {
        List<PrimaryKey> primaryKeys = new ArrayList();
        ResultSet rs = null;

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            rs = metaData.getPrimaryKeys((String)null, schema, tableName);

            while(rs.next()) {
                PrimaryKey obj = new PrimaryKey();
                obj.setColumnName(rs.getString("COLUMN_NAME"));
                obj.setKeySeq(rs.getInt("KEY_SEQ"));
                obj.setPkName(rs.getString("PK_NAME"));
                primaryKeys.add(obj);
            }

            rs.close();
        } catch (SQLException e) {
            logger.error("{}", e);
        }

        return primaryKeys;
    }
}
