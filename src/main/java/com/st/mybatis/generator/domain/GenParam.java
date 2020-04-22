package com.st.mybatis.generator.domain;

import lombok.Data;

/**
 * @ClassName GenParam
 * @Description 参数，表名、包名等
 * @Author songtao
 * @Date 2020/4/22 0022 16:45
 **/
@Data
public class GenParam {
    /**
     * 代码生成的位置
     * e.g basePackage 是 com.st, module 是 test， 那么生成的po,vo,dao 默认路径就是 com.st.test
     */
    private String module;
    /**
     * 需要生成代码的表名
     */
    private String[] tables;
}
