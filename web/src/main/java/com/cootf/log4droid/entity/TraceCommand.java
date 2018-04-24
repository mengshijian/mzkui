package com.cootf.log4droid.entity;

import com.cootf.log4droid.base.entity.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * trace命令实体
 */
@Document(collection = "traceCommand")
public class TraceCommand extends BaseEntity {

    private String userId;

    /**
     * 命令行语句
     */
    private String commandLine;

    /**
     * 命令类型
     */
    private Integer commandType;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public Integer getCommandType() {
        return commandType;
    }

    public void setCommandType(Integer commandType) {
        this.commandType = commandType;
    }
}
