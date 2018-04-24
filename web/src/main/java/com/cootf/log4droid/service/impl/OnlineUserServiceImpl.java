package com.cootf.log4droid.service.impl;

import com.cootf.log4droid.base.query.BaseQuery;
import com.cootf.log4droid.base.service.impl.AbsBaseServiceImpl;
import com.cootf.log4droid.repository.OnLineUserRepository;
import com.cootf.log4droid.service.OnlineUserService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * 在线用户加载
 */
@Service
public class OnlineUserServiceImpl extends AbsBaseServiceImpl implements OnlineUserService {

    @Autowired
    private OnLineUserRepository userRepository;
    @Autowired
    private MongoTemplate template;

    @Override
    @PostConstruct
    protected void init() {
        this.repository = userRepository;
        this.mongoTemplate = template;
    }

    @Override
    public <S extends BaseQuery> Query buildQuery(S queryParam) {
        return new Query();
    }
}
