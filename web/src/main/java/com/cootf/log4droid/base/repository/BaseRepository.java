package com.cootf.log4droid.base.repository;

import com.cootf.log4droid.base.entity.BaseEntity;
import com.cootf.log4droid.controller.vo.DataPageable;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 自定义Repository接口
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID extends Serializable> extends
        MongoRepository<T, ID> {

    /**
     * 使用MongoTemplate进行分页查询
     */
    public DataPageable<T> findByPage(Query query, DataPageable<T> pageable, MongoTemplate template,
            Class<T> cla);
}
