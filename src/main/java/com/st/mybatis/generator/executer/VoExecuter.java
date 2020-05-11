package com.st.mybatis.generator.executer;

import com.mysql.jdbc.StringUtils;
import com.st.mybatis.generator.base.BaseExecuter;
import com.st.mybatis.generator.domain.GenFileInfo;
import com.st.mybatis.generator.schema.Column;
import com.st.mybatis.generator.schema.Table;
import com.st.mybatis.generator.utils.GeneratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName VoExecuter
 * @Description 生成Vo文件
 * @Author songtao
 * @Date 2020/4/22 0022 16:58
 **/
public class VoExecuter extends BaseExecuter {
    private static final Logger log = LoggerFactory.getLogger(VoExecuter.class);
    private GenFileInfo genFileInfo;

    public VoExecuter(){
        super();
    }

    public VoExecuter(GenFileInfo genFileInfo){
        this.genFileInfo = genFileInfo;
    }

    @Override
    public void build(Table table) throws IOException {
        if (table.getPrimaryKeys().size() == 1){
            log.info("\n生成非联合主键VO文件");
            this.buildVo(table);
        }else {
            log.info("\n生成联合主键VO类");
            log.info("\n生成VO文件");
        }
    }


    /**
     * 生成VO
     * @param table
     * @throws IOException
     */
    public void buildVo(Table table) throws IOException{
        List<String> types = table.getColumns().stream().map(Column::getType).collect(Collectors.toList());
        File beanFile = new File(this.genFileInfo.getPath(), this.genFileInfo.getName()+".java");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));

        this.buildClassHeader(bw,table,types,this.genFileInfo.getName());

        for (Column column : table.getColumns()) {
            buildField(bw,column);
        }
        bw.newLine();
        bw.write("}");
        bw.newLine();
        bw.flush();
        bw.close();
    }

    /**
     * @param bw
     * @param table
     * @param types
     * @param className
     * @throws IOException
     */
    private void buildClassHeader(BufferedWriter bw, Table table, List<String> types, String className) throws IOException {
        bw.write("package " + this.genFileInfo.getPackageName() + ";");
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

        buildClassComment(bw, "", classComment);
        bw.newLine();
        bw.write("@Data");
        bw.newLine();
        bw.write("public class " + className + " implements Serializable {");
        bw.newLine();
        bw.write("\tprivate static final long serialVersionUID = 1L;");
        bw.newLine();
    }


    /**
     * 生成字段
     * @param bw
     * @param column
     */
    private void buildField(BufferedWriter bw, Column column) throws IOException {
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
}
