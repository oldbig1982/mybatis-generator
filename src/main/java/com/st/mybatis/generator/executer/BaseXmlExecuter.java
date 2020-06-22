package com.st.mybatis.generator.executer;

import com.st.mybatis.generator.base.BaseExecuter;
import com.st.mybatis.generator.domain.GenConfig;
import com.st.mybatis.generator.domain.GenFileInfo;
import com.st.mybatis.generator.schema.Column;
import com.st.mybatis.generator.schema.PrimaryKey;
import com.st.mybatis.generator.schema.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;


/**
 * @ClassName BaseXmlExecuter
 * @Description 生成base.xml
 * @Author songtao
 * @Date 2020/4/22 0022 16:59
 **/
public class BaseXmlExecuter extends BaseExecuter {

    private static final Logger log = LoggerFactory.getLogger(BaseXmlExecuter.class);
    private GenFileInfo baseMapperXmlInfo;
    private GenFileInfo daoInfo;
    private GenFileInfo poInfo;
    private GenConfig genConfig;

    public BaseXmlExecuter(GenFileInfo baseMapperXmlInfo, GenFileInfo daoInfo, GenFileInfo poInfo,GenConfig genConfig) {
        this.baseMapperXmlInfo = baseMapperXmlInfo;
        this.daoInfo = daoInfo;
        this.poInfo = poInfo;
        this.genConfig = genConfig;
    }

    @Override
    public void build(Table table) throws Exception {
        List<Column> columns = table.getColumns();
        File mapperXmlFile = new File(this.baseMapperXmlInfo.getPath(), this.baseMapperXmlInfo.getName() + ".xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));

        try {
            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            bw.newLine();
            bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
            bw.newLine();
            bw.write("<!-- ============================================================== -->");
            bw.newLine();
            bw.write("<!-- ============================================================== -->");
            bw.newLine();
            bw.write("<!-- =======通过工具自动生成，请勿手工修改！======= -->");
            bw.newLine();
            bw.write("<!-- =======本配置文件中定义的节点可在自定义配置文件中直接使用！       ======= -->");
            bw.newLine();
            bw.write("<!-- ============================================================== -->");
            bw.newLine();
            bw.write("<!-- ============================================================== -->");
            bw.newLine();
            bw.write("<mapper namespace=\"" + this.daoInfo.getPackageName() + "." + this.daoInfo.getName() + "\">");
            if (this.genConfig.isDefaultCache()) {
                bw.newLine();
                bw.write("\t<!-- 默认开启二级缓存,使用Least Recently Used（LRU，最近最少使用的）算法来收回 -->");
                bw.newLine();
                bw.write("\t<cache/>");
            }
            bw.newLine();

            bw.newLine();
            List<PrimaryKey> primaryKeys = table.getPrimaryKeys();
            PrimaryKey primaryKey = primaryKeys.get(0);
            this.buildBaseResultMap(bw, table, primaryKey, columns);
            this.buildBaseColumnList(bw, table, primaryKey, columns);
            this.buildBaseSelectByEntityWhere(bw, table, primaryKey, columns);
            this.buildBaseSelectByEntity(bw, table, primaryKey, columns);
            this.buildSelectByPrimaryKey(bw, table, primaryKey, columns);
            this.buildSelectBatchByPrimaryKeys(bw, table, primaryKey, columns);
            this.buildDeleteByPrimaryKey(bw, table, primaryKey, columns);
            this.buildDeleteBatchByPrimaryKeys(bw, table, primaryKey, columns);
            this.buildInsert(bw, table, primaryKey, columns);
            this.buildInsertSelective(bw, table, primaryKey, columns);
            this.buildUpdateSelectiveByPrimaryKey(bw, table, primaryKey, columns);
            this.buildUpdateByPrimaryKey(bw, table, primaryKey, columns);
            bw.write("</mapper>");
            bw.flush();
        } catch (IOException e) {
           throw e;
        } finally {
            bw.close();
        }
        log.info("Generate BaseXml file " + mapperXmlFile.getAbsolutePath());
    }


    /**
     * 写入通用结果查询对象
     * @param bw
     * @param table
     * @param primaryKey
     * @param columns
     * @throws IOException
     */
    private void buildBaseResultMap(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        int size = columns.size();
        bw.write("\t<!-- 通用查询结果对象-->");
        bw.newLine();
        bw.write("\t<resultMap id=\"BaseResultMap\" type=\"" + this.poInfo.getPackageName() + "." + this.poInfo.getName() + "\">");
        bw.newLine();

        for(int i = 0; i < size; ++i) {
            Column column = columns.get(i);
            if (column.getName().equalsIgnoreCase(primaryKey.getColumnName())) {
                bw.write("\t\t <id ");
            } else {
                bw.write("\t\t <result ");
            }

            bw.write("column=\"" + column.getName() + "\" ");
            bw.write("property=\"" + getInstanceName(column.getName()) + "\"/> ");
            bw.newLine();
        }

        bw.write("\t</resultMap>");
        bw.newLine();
        bw.newLine();
    }

    /**
     * 写入通用查询结果列
     * @param bw
     * @param table
     * @param primaryKey
     * @param columns
     * @throws IOException
     */
    private void buildBaseColumnList(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        int size = columns.size();
        bw.write("\t<!-- 通用查询结果列-->");
        bw.newLine();
        bw.write("\t<sql id=\"Base_Column_List\">");
        bw.newLine();
        bw.write("\t\t");

        for(int i = 0; i < size; ++i) {
            Column column = columns.get(i);
            bw.write(" " + column.getName());
            if (i != size - 1) {
                bw.write(",");
            }

            if (i % 5 == 4) {
                bw.newLine();
                bw.write("\t\t");
            }
        }

        bw.newLine();
        bw.write("\t</sql>");
        bw.newLine();
        bw.newLine();
    }

    /**
     * 写入按对象查询记录的WHERE部分
     * @param bw
     * @param table
     * @param primaryKey
     * @param columns
     * @throws IOException
     */
    private void buildBaseSelectByEntityWhere(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        int size = columns.size();
        bw.write("\t<!-- 按对象查询记录的WHERE部分 -->");
        bw.newLine();
        bw.write("\t<sql id=\"Base_Select_By_Entity_Where\">");
        bw.newLine();

        for(int i = 0; i < size; ++i) {
            Column column = columns.get(i);
            bw.write("\t\t<if test=\"" + getInstanceName(column.getName()) + " != null\" >");
            bw.newLine();
            bw.write("\t\t\tand " + column.getName() + " = #{" + getInstanceName(column.getName()) + "}");
            bw.newLine();
            bw.write("\t\t</if>");
            bw.newLine();

        }

        bw.write("\t</sql>");
        bw.newLine();
        bw.newLine();
    }


    /**
     * 查询语句
     * @param bw
     * @param table
     * @param primaryKey
     * @param columns
     * @throws IOException
     */
    private void buildBaseSelectByEntity(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 按对象查询记录的SQL部分 -->");
        bw.newLine();
        bw.write("\t<sql id=\"Base_Select_By_Entity\">");
        bw.newLine();
        bw.write("\t\tselect");
        bw.newLine();
        bw.write("\t\t\t<include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\tfrom " + table.getName());
        bw.newLine();
        bw.write("\t\t<where>");
        bw.newLine();
        bw.write("\t\t\t<include refid=\"Base_Select_By_Entity_Where\" />");
        bw.newLine();
        bw.write("\t\t</where>");
        bw.newLine();
        bw.write("\t</sql>");
        bw.newLine();
        bw.newLine();
    }

    /**
     * 写入 按主键查询一条记录
     * @param bw
     * @param table
     * @param primaryKey
     * @param columns
     * @throws IOException
     */
    private void buildSelectByPrimaryKey(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 按主键查询一条记录 -->");
        bw.newLine();
        bw.write("\t<select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\"map\">");
        bw.newLine();
        bw.write("\t\tselect");
        bw.newLine();
        bw.write("\t\t\t<include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\tfrom " + table.getName());
        bw.newLine();
        bw.write("\t\twhere " + primaryKey.getColumnName() + " = #{param1}");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
    }

    /**
     * SelectBatchByPrimaryKeys
     * 写入按主键集合查询
     * @param bw
     * @param table
     * @param primaryKey
     * @param columns
     * @throws IOException
     */
    private void buildSelectBatchByPrimaryKeys(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 按主键List查询多条记录 -->");
        bw.newLine();
        bw.write("\t<select id=\"selectBatchByPrimaryKeys\" resultMap=\"BaseResultMap\" parameterType=\"map\">");
        bw.newLine();
        bw.write("\t\tselect");
        bw.newLine();
        bw.write("\t\t\t<include refid=\"Base_Column_List\" />");
        bw.newLine();
        bw.write("\t\tfrom " + table.getName());
        bw.newLine();
        bw.write("\t\twhere " + primaryKey.getColumnName() + " in");
        bw.newLine();
        bw.write("\t\t<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">");
        bw.newLine();
        bw.write("\t\t\t#{item}");
        bw.newLine();
        bw.write("\t\t</foreach>");
        bw.newLine();
        bw.write("\t</select>");
        bw.newLine();
        bw.newLine();
    }


    /**
     * 按主键删除一条记录
     * @param bw
     * @param table
     * @param primaryKey
     * @param columns
     * @throws IOException
     */
    private void buildDeleteByPrimaryKey(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 按主键删除一条记录 -->");
        bw.newLine();
        bw.write("\t<delete id=\"deleteByPrimaryKey\" parameterType=\"map\">");
        bw.newLine();
        bw.write("\t\tdelete from " + table.getName());
        bw.newLine();
        bw.write("\t\twhere " + primaryKey.getColumnName() + " = #{param1}");
        bw.newLine();
        bw.write("\t</delete>");
        bw.newLine();
        bw.newLine();
    }


    /**
     * 按主键List删除多条记录
     * @param bw
     * @param table
     * @param primaryKey
     * @param columns
     * @throws IOException
     */
    private void buildDeleteBatchByPrimaryKeys(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 按主键List删除多条记录 -->");
        bw.newLine();
        bw.write("\t<delete id=\"deleteBatchByPrimaryKeys\" parameterType=\"map\">");
        bw.newLine();

        bw.write("\t\tdelete from " + table.getName());

        bw.newLine();
        bw.write("\t\twhere " + primaryKey.getColumnName() + " in ");
        bw.newLine();
        bw.write("\t\t<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">");
        bw.newLine();
        bw.write("\t\t\t#{item}");
        bw.newLine();
        bw.write("\t\t</foreach>");
        bw.newLine();
        bw.write("\t</delete>");
        bw.newLine();
        bw.newLine();
    }


    /**
     * 完整插入一条记录
     * @param bw
     * @param table
     * @param primaryKey
     * @param columns
     * @throws IOException
     */
    private void buildInsert(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        int size = columns.size();
        bw.write("\t<!-- 完整插入一条记录-->");
        bw.newLine();
        bw.write("\t<insert id=\"insert\" parameterType=\"" + this.poInfo.getPackageName() + "." + this.poInfo.getName() + "\">");
        bw.newLine();
        bw.write("\t\tinsert into " + table.getName() + " (");
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < size; ++i) {
            Column column = columns.get(i);
            sb.append(column.getName());
            if (i != size - 1) {
                sb.append(", ");
            }
            //每5个字段换一行
            if (i % 5 == 4) {
                sb.append("\r\n\t\t\t");
            }
        }

        //删除最后一个逗号
        if (sb.toString().endsWith(", \r\n\t\t\t")) {
            sb.delete(0, sb.length() - ", \r\n\t\t\t".length());
        }

        bw.write(sb.toString());
        bw.write(")");
        bw.newLine();
        bw.write("\t\tvalues(");
        sb.setLength(0);

        for(int i = 0; i < size; ++i) {
            Column column = columns.get(i);

            sb.append("#{" + getInstanceName(column.getName()));
            sb.append("}");

            if (i != size - 1) {
                sb.append(", ");
            }
            if (i % 5 == 4) {
                sb.append("\r\n\t\t\t");
            }
        }
        if (sb.toString().endsWith(", \r\n\t\t\t")) {
            sb.delete(0, sb.length() - ", \r\n\t\t\t".length());
        }

        bw.write(sb.toString());
        bw.write(")");
        bw.newLine();
        bw.write("\t</insert>");
        bw.newLine();
        bw.newLine();
    }

    /**
     * 插入一条记录(为空的字段不操作)
     * @param bw
     * @param table
     * @param primaryKey
     * @param columns
     * @throws IOException
     */
    private void buildInsertSelective(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 插入一条记录(为空的字段不操作) -->");
        bw.newLine();
        bw.write("\t<insert id=\"insertSelective\" parameterType=\"" + this.poInfo.getPackageName() + "." + this.poInfo.getName() + "\">");
        bw.newLine();
        bw.write("\t\tinsert into " + table.getName() + "");
        bw.newLine();
        bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
        bw.newLine();

        for (Column column : columns) {
            bw.write("\t\t\t<if test=\"" + getInstanceName(column.getName()) + " != null\" >");
            bw.newLine();
            bw.write("\t\t\t\t" + column.getName() + ",");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }

        bw.write("\t\t</trim>");
        bw.newLine();
        bw.write("\t\tvalues <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
        bw.newLine();

        for (Column column : columns) {
            bw.write("\t\t\t<if test=\"" + getInstanceName(column.getName()) + " != null\" >");
            bw.newLine();
            bw.write("\t\t\t\t#{" + getInstanceName(column.getName()) + "},");
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }

        bw.write("\t\t</trim>");
        bw.newLine();
        bw.write("\t</insert>");
        bw.newLine();
        bw.newLine();
    }


    /**
     * 更新一条记录(为空的字段不操作)
     * @param bw
     * @param table
     * @param primaryKey
     * @param columns
     * @throws IOException
     */
    private void buildUpdateSelectiveByPrimaryKey(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 更新一条记录(为空的字段不操作) -->");
        bw.newLine();
        bw.write("\t<update id=\"updateSelectiveByPrimaryKey\" parameterType=\"" + this.poInfo.getPackageName() + "." + this.poInfo.getName() + "\">");
        bw.newLine();
        bw.write("\t\tupdate " + table.getName());
        bw.newLine();
        bw.write("\t\t<set>");
        bw.newLine();

        for (Column column : columns) {
            bw.write("\t\t\t<if test=\"" + getInstanceName(column.getName()) + " != null\" >");
            bw.newLine();
            bw.write("\t\t\t\t" + column.getName() + "=#{" + getInstanceName(column.getName() + "},"));
            bw.newLine();
            bw.write("\t\t\t</if>");
            bw.newLine();
        }

        bw.write("\t\t</set>");
        bw.newLine();
        bw.write("\t\twhere " + primaryKey.getColumnName() + " = #{" + getInstanceName(primaryKey.getColumnName()) + "}");
        bw.newLine();
        bw.write("\t</update>");
        bw.newLine();
        bw.newLine();
    }

    /**
     * 完整更新一条记录
     * @param bw
     * @param table
     * @param primaryKey
     * @param columns
     * @throws IOException
     */
    private void buildUpdateByPrimaryKey(BufferedWriter bw, Table table, PrimaryKey primaryKey, List<Column> columns) throws IOException {
        bw.write("\t<!-- 完整更新一条记录 -->");
        bw.newLine();
        bw.write("\t<update id=\"updateByPrimaryKey\" parameterType=\"" + this.poInfo.getPackageName() + "." + this.poInfo.getName() + "\">");
        bw.newLine();
        bw.write("\t\tupdate " + table.getName());
        bw.newLine();
        bw.write("\t\tset ");

        for(int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            bw.write(column.getName() + "=#{" + getInstanceName(column.getName() + "}"));
            if (i !=  columns.size() - 1) {
                bw.write(",");
                bw.newLine();
                bw.write("\t\t\t");
            }
        }
        bw.newLine();
        bw.write("\t\twhere " + primaryKey.getColumnName() + " = #{" + getInstanceName(primaryKey.getColumnName()) + "}");
        bw.newLine();
        bw.write("\t</update>");
        bw.newLine();
        bw.newLine();
    }


}
