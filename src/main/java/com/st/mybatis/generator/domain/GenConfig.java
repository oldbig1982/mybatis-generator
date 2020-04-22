package com.st.mybatis.generator.domain;


import com.st.mybatis.generator.enums.GenType;

/**
 * @ClassName GenConfig
 * @Description 生成代码的个性化配置
 * @Author songtao
 * @Date 2020/4/22 0022 13:14
 **/
public class GenConfig {
    /**
     * 项目包名
     */
    private String basePackage;
    /**
     * 基本保存目录（Java源代码根目录）
     */
    protected String saveDir;
    /**
     * vo保存路径 (默认生成在指定的module.vo包下)
     */
    protected String saveDirForVo;
    /**
     * xml保存路径（默认生成在resources/mapper/）
     */
    protected String saveDirForXml;
    /**
     *需要生成哪些文件
     */
    private GenType[] genTypes;
    /**
     * 要忽略的表名前缀，默认空
     */
    protected String[] ignoreTablePrefixs = null;
    /**
     * 设置PO是否保留前缀(设置忽略表名前缀时)，默认true
     */
    protected boolean keepPrefixForPO = true;
    /**
     * 表名
     */
    protected String[] tableNames = null;
    /**
     * 设置是否默认开启二级缓存（影响base中的MapperXML），默认false
     */
    protected boolean defaultCache = false;

    protected boolean dbColumnUnderline = false;
    /**
     * 数据库驱动
     */
    protected String dbDriverName;
    /**
     * 数据库用户名
     */
    protected String dbUser;
    /**
     * 数据库用户密码
     */
    protected String dbPassword;
    /**
     * 数据库连接
     */
    protected String dbUrl;
    /**
     * 数据库名
     */
    protected String dbSchema;

}
