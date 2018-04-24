package com.cootf.log4droid.base.service.impl;

import com.cootf.log4droid.base.entity.BaseEntity;
import com.cootf.log4droid.base.query.BaseQuery;
import com.cootf.log4droid.base.repository.BaseRepository;
import com.cootf.log4droid.base.service.BaseService;
import com.cootf.log4droid.controller.vo.DataPageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public abstract class AbsBaseServiceImpl implements BaseService {

    protected BaseRepository repository;

    protected MongoTemplate mongoTemplate;

    protected abstract void init();

    @Override
    public <T extends BaseEntity> T saveOne(T entity) {
        T t = (T) repository.insert(entity);
        return t;
    }

    @Override
    public void deleteById(String id) {
        repository.delete(id);
    }

    @Override
    public <T extends BaseEntity, S extends BaseQuery> DataPageable<T> findByPage(S queryParam,
            DataPageable<T> pageable, Class<T> cla) {
        Query query = buildQuery(queryParam);
        return repository.findByPage(query, pageable, mongoTemplate, cla);
    }
}
