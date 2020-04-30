package com.st.mybatis.generator.enums;

/**
 * 生成文件类型
 */
public enum GenType {
    VO,
    PO,
    DAO,
    SERVICE,
    API,
    BASE_MAPPER_XML,
    MAPPER_XML;

    private GenType() {
    }
}
