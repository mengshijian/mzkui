package com.cootf.log4droid.service;

import com.cootf.log4droid.base.service.BaseService;

/**
 * trace命令服务接口
 */
public interface TraceCommandService extends BaseService {

    /**
     * 根据用户id删除其未下发的trade命令
     */
    boolean deleteByStatus(String userId, Integer status);
}
