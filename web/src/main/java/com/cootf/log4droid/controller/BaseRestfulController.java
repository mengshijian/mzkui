package com.cootf.log4droid.controller;

import com.cootf.log4droid.controller.vo.ResResult;

public abstract class BaseRestfulController {

    private static final String SUCCESS_CODE = "0";

    private static final String SUCCESS_MSG = "成功";

    private static final String FAIL_CODE = "-1";

    private static final String FAIL_MSG = "失败";

    /**
     * 调用成功
     */
    protected <T> ResResult<T> resultSuccess(T... data) {
        ResResult<T> result = new ResResult<T>();
        result.setCode(SUCCESS_CODE);
        if (data.length > 0) {
            result.setData(data[0]);
        }
        result.setMsg(SUCCESS_MSG);
        return result;
    }

    /**
     * 调用失败
     */
    protected <T> ResResult<T> resultFail(T... data) {
        ResResult<T> result = new ResResult<T>();
        result.setCode(FAIL_CODE);
        result.setData(data.length > 0 ? data[0] : null);
        result.setMsg(FAIL_MSG);
        return result;
    }

}
