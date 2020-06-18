package com.st.mybatis.generator.executer;

import com.st.mybatis.generator.base.BaseExecuter;
import com.st.mybatis.generator.domain.GenConfig;
import com.st.mybatis.generator.domain.GenFileInfo;
import com.st.mybatis.generator.schema.Column;
import com.st.mybatis.generator.schema.Table;
import com.st.mybatis.generator.utils.GeneratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * @ClassName PoExecuter
 * @Description 生成Po文件
 * @Author songtao
 * @Date 2020/4/22 0022 16:58
 **/
public class PoExecuter extends BaseExecuter {
    private static final Logger log = LoggerFactory.getLogger(PoExecuter.class);
    private GenFileInfo poInfo;

    public PoExecuter(GenConfig genConfig, GenFileInfo poInfo) {
        this.poInfo = poInfo;
    }

    @Override
    public void build(Table table) throws Exception{
        if (table.getPrimaryKeys().size() == 1) {
            this.buildPo(table);
        } else {
           throw new IllegalAccessException("暂不支持联合主键");
        }

    }

    /**
     * 写入 package， import 及类名
     * @param bw
     * @param table
     * @param types
     * @param className
     * @param info
     * @throws IOException
     */
    private void buildClassHeader(BufferedWriter bw, Table table, List<String> types, String className, String info) throws IOException {
        bw.write(String.format("package %s;", this.poInfo.getPackageName()));
        bw.newLine();
        bw.newLine();
        bw.write("import java.io.Serializable;");
        bw.newLine();
        if (GeneratorUtils.haveDate(types)) {
            bw.write("import java.util.Date;");
            bw.newLine();
        }

        if (GeneratorUtils.haveDecimal(types)) {
            bw.write("import java.math.BigDecimal;");
            bw.newLine();
        }

        bw.newLine();
        bw.write("import lombok.Data;");
        bw.newLine();
        String classComment = "对应表名：" + table.getName();
        if (table.getComment() != null && table.getComment().trim().length() > 0) {
            classComment = classComment + ",备注：" + table.getComment().trim();
        }

        buildClassComment(bw, String.format("通过工具自动生成，请勿手工修改。表%s的%s<br/>", table.getName(), info), classComment);
        bw.newLine();
        bw.write("@Data");
        bw.newLine();
        bw.write(String.format("public class %s implements Serializable {", className));
        bw.newLine();
        bw.write("\tprivate static final long serialVersionUID = 1L;");
        bw.newLine();
    }

    /**
     * 写入Po属性字段
     * @param bw
     * @param column
     * @throws IOException
     */
    private void buildFieldGetterSetter(BufferedWriter bw, Column column) throws IOException {
        String field = getInstanceName(column.getName());
        bw.write("\t/** 对应字段：" + column.getName());
        String comment = column.getComment();
        if (comment != null && comment.trim().length() > 0) {
            bw.write(",备注：" + comment.trim());
        }

        bw.write(" */");
        bw.newLine();
        bw.write("\tprivate " + processType(column) + " " + field + ";");
        bw.newLine();
    }


    /**
     * 生成Po文件
     * @param table
     * @throws IOException
     */
    public void buildPo(Table table) throws IOException {
        List<String> types = GeneratorUtils.getTableColumnTypes(table);
        File beanFile = new File(this.poInfo.getPath(), this.poInfo.getName() + ".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));

        try {
            this.buildClassHeader(bw, table, types, this.poInfo.getName(), "PO对象");
            for (Column column : table.getColumns()) {
                buildFieldGetterSetter(bw,column);
            }
            bw.newLine();
            bw.write("}");
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            bw.close();
        }
        log.info("Generate PO file " + beanFile.getAbsolutePath());
    }
}
