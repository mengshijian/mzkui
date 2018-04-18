package com.cootf.log4droid.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.cootf.log4droid.repository")
@Configuration
public class MongoConfiguration {


}
