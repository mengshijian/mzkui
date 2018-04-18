package com.cootf.log4droid.entity;

import java.io.Serializable;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 提取日志命令实体
 */
@Document(collection = "extractLog")
public class ExtractLog extends BaseEntity {


}
