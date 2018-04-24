package com.cootf.log4droid.base.service;

import com.cootf.log4droid.base.entity.BaseEntity;
import com.cootf.log4droid.base.query.BaseQuery;
import com.cootf.log4droid.controller.vo.DataPageable;
import org.springframework.data.mongodb.core.query.Query;

/**
 * 基础服务接口
 */
public interface BaseService {

    /**
     * 保存对象
     */
    <T extends BaseEntity> T saveOne(T entity);

    /**
     * 根据t参数删除对象
     */
    void deleteById(String id);

    /**
     * 通过分页查找数据
     */
    <T extends BaseEntity, S extends BaseQuery> DataPageable<T> findByPage(S queryParam,
            DataPageable<T> pageable, Class<T> cla);

    /**
     * mongo查询对象构建
     */
    <S extends BaseQuery> Query buildQuery(S queryParam);
}
