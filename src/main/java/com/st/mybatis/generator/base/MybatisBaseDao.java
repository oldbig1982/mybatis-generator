package com.st.mybatis.generator.base;

import java.util.List;

/**
 * @ClassName MybatisBaseDao
 * @Description Dao常用公共方法
 * @Author songtao
 * @Date 2020/6/18 0018 20:46
 **/
public interface MybatisBaseDao<T, I>{

    /**
     * 按主键查询一条记录
     * @param id
     * @return
     */
    T selectByPrimaryKey(I id);

    /**
     * 按主键List查询多条记录
     * @param ids
     * @return
     */
    List<T> selectBatchByPrimaryKeys(List<I> ids);

    /**
     * 按主键删除一条记录
     * @param id
     * @return
     */
    int deleteByPrimaryKey(I id);

    /**
     * 按主键List删除多条记录
     * @param ids
     * @return
     */
    int deleteBatchByPrimaryKeys(List<I> ids);

    /**
     * 完整插入一条数据
     * @param po
     * @return
     */
    int insert(T po);

    /**
     * 插入一条记录(为空的字段不操作)
     * @param po
     * @return
     */
    int insertSelective(T po);

    /**
     * 完整更新一条记录
     * @param po
     * @return
     */
    int updateByPrimaryKey(T po);

    /**
     * 更新一条记录(为空的字段不操作)
     * @param po
     * @return
     */
    int updateSelectiveByPrimaryKey(T po);

}
