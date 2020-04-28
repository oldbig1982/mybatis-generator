package com.st.mybatis.generator.base;

import com.st.mybatis.generator.domain.GenConfig;
import com.st.mybatis.generator.domain.GenParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName BaseGenerator
 * @Description 代码生成器
 * @Author songtao
 * @Date 2020/4/22 0022 16:44
 **/
public abstract class BaseGenerator {
    private static final Logger log = LoggerFactory.getLogger(BaseGenerator.class);
    private static final String JAVA_SUFFIX = ".java";
    private static final String XML_SUFFIX = ".xml";
    private GenConfig genConfig;
    private GenParam genParam;

    public BaseGenerator(){
        super();
    }

    public void setGenConfig(GenConfig genConfig) {
        this.genConfig = genConfig;
    }

    public void setGenParam(GenParam genParam) {
        this.genParam = genParam;
    }



}
