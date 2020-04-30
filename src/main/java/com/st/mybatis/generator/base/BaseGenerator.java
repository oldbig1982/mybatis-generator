package com.st.mybatis.generator.base;

import com.st.mybatis.generator.domain.GenConfig;
import com.st.mybatis.generator.domain.GenParam;
import com.st.mybatis.generator.enums.GenType;
import com.st.mybatis.generator.schema.Database;
import com.st.mybatis.generator.schema.Table;
import com.st.mybatis.generator.utils.DatabaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName BaseGenerator
 * @Description 代码生成器
 * @Author songtao
 * @Date 2020/4/22 0022 16:44
 **/
public abstract class BaseGenerator {
    private static final Logger log = LoggerFactory.getLogger(BaseGenerator.class);
    protected static final String JAVA_SUFFIX = ".java";
    protected static final String XML_SUFFIX = ".xml";
    protected GenConfig genConfig;
    protected List<GenParam> genParam;

    public BaseGenerator(){
        super();
    }

    public void setGenConfig(GenConfig genConfig) {
        this.genConfig = genConfig;
    }

    public void setGenParam(List<GenParam> genParam) {
        this.genParam = genParam;
    }


    /**
     * 生成代码入口
     */
    public void generate(){

        this.checkConfig();
        try {
            Class.forName(genConfig.getDbDriverName());
            Properties props = new Properties();
            props.setProperty("user", genConfig.getDbUser());
            props.setProperty("password", genConfig.getDbPassword());
            props.setProperty("remarks", "true");
            props.setProperty("useInformationSchema", "true");
            Connection conn = DriverManager.getConnection(genConfig.getDbUrl(), props);
            DatabaseUtil databaseUtil = DatabaseUtil.getInstance(conn,null);
            Map<String,String> allTables = databaseUtil.getAllTableNamesMap();
            for (GenParam param : genParam) {
                String module = param.getModule();
                module = module.trim().toLowerCase();
                String[] tables = param.getTables();
                if (tables.length >0){
                    for (int i = 0; i < tables.length; i++) {
                        String tableName = tables[i];
                        if (allTables.containsKey(tableName.toLowerCase())){
                            Table table = databaseUtil.getTableInfo(tableName);
                            this.run(module,table);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 生成每一个表的相关代码
     * @param modual
     * @param table
     */
    public abstract void run(String modual,  Table table);


    /**
     * 校验参数合理性
     */
    private void checkConfig(){

    }


    /**
     * 判断是否生成genType文件
     * @param genType
     * @return
     */
    protected boolean containsGenType(GenType genType) {
        GenType[] genTypes = this.genConfig.getGenTypes();
        int length = genTypes.length;
        for(int i = 0; i < length; ++i) {
            GenType gen = genTypes[i];
            if (gen == genType) {
                return true;
            }
        }
        return false;
    }
}
