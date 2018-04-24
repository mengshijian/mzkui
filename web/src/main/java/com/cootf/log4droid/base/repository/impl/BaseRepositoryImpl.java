package com.cootf.log4droid.base.repository.impl;

import com.cootf.log4droid.base.entity.BaseEntity;
import com.cootf.log4droid.base.repository.BaseRepository;
import com.cootf.log4droid.controller.vo.DataPageable;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

public class BaseRepositoryImpl<T extends BaseEntity, ID extends Serializable> extends
        SimpleMongoRepository<T, ID> implements
        BaseRepository<T, ID> {

    public BaseRepositoryImpl(
            MongoEntityInformation<T, ID> metadata,
            MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
    }

    @Override
    public DataPageable<T> findByPage(Query query, DataPageable<T> pageable, MongoTemplate template,
            Class<T> cla) {
        if (template != null) {
            pageable.setData((List<T>) template.find(query.with(pageable), cla));
            pageable.setTotal(template.count(query, cla));
        }
        return pageable;
    }
}
