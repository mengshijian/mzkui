package com.cootf.log4droid.service.impl;

import com.cootf.log4droid.base.query.BaseQuery;
import com.cootf.log4droid.base.service.impl.AbsBaseServiceImpl;
import com.cootf.log4droid.entity.TraceCommand;
import com.cootf.log4droid.repository.TraceCommandRepository;
import com.cootf.log4droid.service.TraceCommandService;
import com.mongodb.WriteResult;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * trace 命令服务实现
 */
@Service
public class TraceCommandServiceImpl extends AbsBaseServiceImpl implements TraceCommandService {

    @Autowired
    private TraceCommandRepository commandRepository;

    @Autowired
    private MongoTemplate template;

    @Override
    @PostConstruct
    protected void init() {
        this.repository = commandRepository;
        this.mongoTemplate = template;
    }

    @Override
    public <S extends BaseQuery> Query buildQuery(S queryParam) {
        return new Query();
    }

    @Override
    public boolean deleteByStatus(String userId, Integer status) {
        WriteResult result = mongoTemplate.remove(new Query(Criteria.where("userId").is(userId))
                        .addCriteria(Criteria.where("status").is(status)),
                TraceCommand.class);
        return result.getN() > 0;
    }

}
