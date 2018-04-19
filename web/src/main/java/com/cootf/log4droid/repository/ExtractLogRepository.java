package com.cootf.log4droid.repository;

import com.cootf.log4droid.entity.ExtractLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExtractLogRepository extends MongoRepository<ExtractLog, String> {


}
