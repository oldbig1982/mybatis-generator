package com.st.mybatis.generator.domain;

import lombok.Data;

/**
 * @ClassName GenFileInfo
 * @Description 生成文件所需参数
 * @Author songtao
 * @Date 2020/5/10 0011 11:23
 **/
@Data
public class GenFileInfo {
    /**
     * 文件名
     */
    private String name;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 路径
     */
    private String path;
}
