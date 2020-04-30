import org.junit.Test;

/**
 * @ClassName TestFile
 * @Description TODO
 * @Author songtao
 * @Date 2020/4/30 0030 14:02
 **/
public class TestFile {
    @Test
    public void testSystem(){
        String sysName = System.getenv("path");
        System.out.println(sysName);
    }
}
