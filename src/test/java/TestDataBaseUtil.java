import com.st.mybatis.generator.domain.GenConfig;
import com.st.mybatis.generator.schema.Table;
import com.st.mybatis.generator.utils.DatabaseUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @ClassName TestDataBaseUtil
 * @Description  测试
 * @Author songtao
 * @Date 2020/4/28 0028 16:09
 **/
public class TestDataBaseUtil {
    @Test
    public void testOracleDataBase(){
        GenConfig genConfig = new GenConfig();
        genConfig.setDbDriverName("oracle.jdbc.driver.OracleDriver");
        genConfig.setDbPassword("xxx");
        genConfig.setDbUser("xxx");
        genConfig.setDbUrl("jdbc:oracle:thin:@xxxx");

        try {
            Class.forName(genConfig.getDbDriverName());
            Properties props = new Properties();
            props.setProperty("user", genConfig.getDbUser());
            props.setProperty("password", genConfig.getDbPassword());
            props.setProperty("remarks", "true");
            props.setProperty("useInformationSchema", "true");
            Connection conn = DriverManager.getConnection(genConfig.getDbUrl(), props);
            DatabaseUtil databaseUtil = DatabaseUtil.getInstance(conn,null);
            Table table = databaseUtil.getTableInfo("PRPDRISK");
            System.out.println(table);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testMysqlDataBase(){
        GenConfig genConfig = new GenConfig();
        genConfig.setDbDriverName("com.mysql.jdbc.Driver");
        genConfig.setDbPassword("xxx");
        genConfig.setDbUser("xxx");
        genConfig.setDbUrl("jdbc:mysql://xxx.xxx.xxx/minecraft?characterEncoding=UTF-8");

        try {
            Class.forName(genConfig.getDbDriverName());
            Properties props = new Properties();
            props.setProperty("user", genConfig.getDbUser());
            props.setProperty("password", genConfig.getDbPassword());
            props.setProperty("remarks", "true");
            props.setProperty("useInformationSchema", "true");
            Connection conn = DriverManager.getConnection(genConfig.getDbUrl(), props);
            DatabaseUtil databaseUtil = DatabaseUtil.getInstance(conn,null);
            Map<String, String> allTableNamesMap = databaseUtil.getAllTableNamesMap();
            Set<String> keySet = allTableNamesMap.keySet();
            for (String s : keySet) {
                System.out.println(allTableNamesMap.get(s));
                Table table = databaseUtil.getTableInfo(s);
                System.out.println(table);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
