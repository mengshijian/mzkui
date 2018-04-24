package com.cootf.log4droid.base.support;

import static org.springframework.data.querydsl.QueryDslUtils.QUERY_DSL_PRESENT;

import com.cootf.log4droid.base.entity.BaseEntity;
import com.cootf.log4droid.base.repository.impl.BaseRepositoryImpl;
import java.io.Serializable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * 用于生成自扩展的Repository方法，比如softDelete Created by mengsj on 2018/04/21.
 */
public class CustomMongoRepositoryFactoryBean<T extends MongoRepository<S, ID>, S extends BaseEntity, ID extends Serializable>
        extends MongoRepositoryFactoryBean<T, S, ID> {

    public CustomMongoRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
        return new LCRRepositoryFactory(operations);
    }

    private static class LCRRepositoryFactory<S extends BaseEntity, ID extends Serializable> extends
            MongoRepositoryFactory {

        private final MongoOperations mongoOperations;

        public LCRRepositoryFactory(MongoOperations mongoOperations) {
            super(mongoOperations);
            this.mongoOperations = mongoOperations;
        }

        @Override
        protected Object getTargetRepository(RepositoryInformation information) {
            Class<?> repositoryInterface = information.getRepositoryInterface();
            MongoEntityInformation<?, Serializable> entityInformation = getEntityInformation(
                    information.getDomainType());
            if (isQueryDslRepository(repositoryInterface)) {
                return new QueryDslMongoRepository(entityInformation, mongoOperations);
            } else {
                return new BaseRepositoryImpl<S, ID>(
                        (MongoEntityInformation<S, ID>) entityInformation, this.mongoOperations);
            }
        }

        private static boolean isQueryDslRepository(Class<?> repositoryInterface) {
            return QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class
                    .isAssignableFrom(repositoryInterface);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return isQueryDslRepository(metadata.getRepositoryInterface())
                    ? QueryDslMongoRepository.class
                    : BaseRepositoryImpl.class;
        }
    }
}
