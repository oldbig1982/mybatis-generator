package com.st.mybatis.generator;

import com.st.mybatis.generator.base.BaseGenerator;
import com.st.mybatis.generator.enums.GenType;
import com.st.mybatis.generator.schema.Table;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName Generator
 * @Description 测试类
 * @Author songtao
 * @Date 2020/4/22 0022 11:53
 **/
public class Generator extends BaseGenerator {
    private static final Logger log = LoggerFactory.getLogger(Generator.class);

    @Override
    public void run(String modual, Table table) {

        //生成VO
        if (containsGenType(GenType.VO)){

        }

        //生成PO
        if (containsGenType(GenType.PO)){
            //校验是否需要生成，如果有PO，是否覆盖

        }

        //生成Dao
        if (containsGenType(GenType.DAO)){

        }

        //生成Service
        if (containsGenType(GenType.SERVICE)){

        }

        //生成Controller
        if (containsGenType(GenType.API)){

        }

        //生成BaseXML
        if (containsGenType(GenType.BASE_MAPPER_XML)){

        }

        //生成自定义xml
        if (containsGenType(GenType.MAPPER_XML)){

        }



    }
}
