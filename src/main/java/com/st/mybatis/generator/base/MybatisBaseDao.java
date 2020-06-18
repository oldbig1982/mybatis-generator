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
     * 完整插入一条数据
     * @param po
     * @return
     */
    int insert(T po);

    /**
     * 只插入不为空的字段
     * @param po
     * @return
     */
    int insertSelective(T po);

    /**
     * 根据主键删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(I id);

    /**
     * 删除主键集合中的所有数据
     * @param ids
     * @return
     */
    int deleteBatchByPrimaryKeys(List<I> ids);

    /**
     * 更新
     * @param po
     * @return
     */
    int updateByPrimaryKey(T po);

    /**
     * 只更新po中不为空的属性
     * @param po
     * @return
     */
    int updateSelectiveByPrimaryKey(T po);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    T selectByPrimaryKey(I id);

    /**
     * 根据主键集合查询
     * @param ids
     * @return
     */
    List<T> selectBatchByPrimaryKeys(List<I> ids);

}
