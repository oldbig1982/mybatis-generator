package com.st.mybatis.generator.executer;

import com.st.mybatis.generator.base.BaseExecuter;
import com.st.mybatis.generator.domain.GenConfig;
import com.st.mybatis.generator.domain.GenFileInfo;
import com.st.mybatis.generator.schema.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @ClassName CustomerXmlExeciter
 * @Description 生成自定义xml
 * @Author songtao
 * @Date 2020/4/22 0022 17:00
 **/
public class CustomerXmlExeciter extends BaseExecuter {
    private static final Logger log = LoggerFactory.getLogger(CustomerXmlExeciter.class);
    private GenFileInfo mapperXmlInfo;
    private GenFileInfo daoInfo;
    private GenConfig genConfig;

    public CustomerXmlExeciter(GenConfig genConfig, GenFileInfo mapperXmlInfo, GenFileInfo daoInfo) {
        this.genConfig = genConfig;
        this.mapperXmlInfo = mapperXmlInfo;
        this.daoInfo = daoInfo;

    }

    public void build(Table table) throws IOException {
        File mapperXmlFile = new File(this.mapperXmlInfo.getPath(), this.mapperXmlInfo.getName() + ".xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));
        try {
            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            bw.newLine();
            bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
            bw.newLine();
            bw.write("<!-- ============================================================== -->");
            bw.newLine();
            bw.write("<!-- ================可直接使用Base配置文件中定义的节点！================ -->");
            bw.newLine();
            bw.write("<!-- ============================================================== -->");
            bw.newLine();
            bw.write("<mapper namespace=\"" + this.daoInfo.getPackageName() + "." + this.daoInfo.getName() + "\">");
            bw.newLine();
            bw.write("  <!-- 请在下方添加自定义配置-->");
            bw.newLine();
            bw.newLine();
            bw.newLine();
            bw.write("</mapper>");
            bw.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
        log.info("Generate Xml file " + mapperXmlFile.getAbsolutePath());
    }

}
