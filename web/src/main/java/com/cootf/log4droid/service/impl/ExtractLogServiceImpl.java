package com.cootf.log4droid.service.impl;

import com.cootf.log4droid.base.query.BaseQuery;
import com.cootf.log4droid.base.service.impl.AbsBaseServiceImpl;
import com.cootf.log4droid.repository.ExtractLogRepository;
import com.cootf.log4droid.service.ExtractLogService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * 提现日志服务实现
 */
@Service
public class ExtractLogServiceImpl extends AbsBaseServiceImpl implements ExtractLogService {

    @Autowired
    private ExtractLogRepository logRepository;

    @Autowired
    private MongoTemplate template;

    @Override
    @PostConstruct
    protected void init() {
        this.repository = logRepository;
        this.mongoTemplate = template;
    }

    @Override
    public <S extends BaseQuery> Query buildQuery(S queryParam) {
        return new Query();
    }
}
