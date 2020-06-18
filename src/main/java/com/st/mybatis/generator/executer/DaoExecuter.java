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
 * @ClassName DaoExecuter
 * @Description 生成Dao.java
 * @Author songtao
 * @Date 2020/4/22 0022 16:57
 **/
public class DaoExecuter extends BaseExecuter {

    private static final Logger log = LoggerFactory.getLogger(DaoExecuter.class);
    private GenFileInfo daoInfo;
    private GenFileInfo poInfo;

    public DaoExecuter(GenFileInfo daoInfo, GenFileInfo poInfo) {
        this.daoInfo = daoInfo;
        this.poInfo = poInfo;
    }

    @Override
    public void build(Table table) throws IOException {
        List<PrimaryKey> primaryKeys = table.getPrimaryKeys();
        if (primaryKeys.size() != 1) {
            throw new IllegalArgumentException("目前只支持单一主键的表");
        } else {
            PrimaryKey primaryKey = (PrimaryKey)primaryKeys.get(0);
            Column idColumn = null;
            List<Column> columns = table.getColumns();
            int size = columns.size();

            for(int i = 0; i < size; ++i) {
                Column column = columns.get(i);
                if (column.getName().equalsIgnoreCase(primaryKey.getColumnName())) {
                    idColumn = column;
                    break;
                }
            }

            if (idColumn == null) {
                throw new IllegalArgumentException("找不到主键名对应的字段");
            } else {
                File mapperFile = new File(this.daoInfo.getPath(), this.daoInfo.getName() + ".java");
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
                try {
                    bw.write("package " + this.daoInfo.getPackageName() + ";");
                    bw.newLine();
                    bw.newLine();
                    bw.write("import org.apache.ibatis.annotations.Mapper;");
                    bw.newLine();
                    bw.newLine();
                    bw.write("import " + this.poInfo.getPackageName() + "." + this.poInfo.getName() + ";");
                    bw.newLine();
                    bw.write("import com.st.mybatis.generator.base.MybatisBaseDao;");
                    bw.newLine();
                    buildClassComment(bw, "", "表" + table.getName() + "对应的基于MyBatis实现的Dao接口<br/>\r\n * 在其中添加自定义方法");
                    bw.newLine();
                    bw.write("@Mapper");
                    bw.newLine();
                    bw.write("public interface " + this.daoInfo.getName() + " extends MybatisBaseDao<" + this.poInfo.getName() + ", " + processType(idColumn) + "> {");
                    bw.newLine();
                    bw.newLine();
                    bw.write("}");
                    bw.flush();
                } catch (IOException e) {
                    throw e;
                } finally {
                    bw.close();
                }
                log.info("Generate Dao file " + mapperFile.getAbsolutePath());
            }
        }
    }
}
