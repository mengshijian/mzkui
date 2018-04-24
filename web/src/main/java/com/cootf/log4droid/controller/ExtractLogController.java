package com.cootf.log4droid.controller;

import com.cootf.log4droid.base.query.BaseQuery;
import com.cootf.log4droid.common.uenum.SendState;
import com.cootf.log4droid.controller.qo.ExtractLogQuery;
import com.cootf.log4droid.controller.vo.DataPageable;
import com.cootf.log4droid.controller.vo.ResResult;
import com.cootf.log4droid.entity.ExtractLog;
import com.cootf.log4droid.service.ExtractLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ExtensionProperty;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提取日志控制器
 */
@RestController
@RequestMapping("/extractLog")
@Api(value = "提取日志restful接口")
public class ExtractLogController extends com.cootf.log4droid.controller.BaseRestfulController {

    @Autowired
    private ExtractLogService logService;

    @ApiOperation(value = "保存提取日志请求", notes = "参数为json格式,")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResResult<ExtractLog> save(@RequestBody ExtractLog log) {
        if (StringUtils.isNotBlank(log.getUserId())) {
            log.setCreateTime(new Date());
            log.setStatus(SendState.INIT.getCode());
            ExtractLog temp = logService.saveOne(log);
            return resultSuccess(temp);
        }
        return resultFail(new ExtractLog());
    }

    @ApiOperation(value = "分页查询提取日志列表", notes = "参数为json格式")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public DataPageable<ExtractLog> findByPage(@RequestBody ExtractLogQuery queryEntity) {
        DataPageable<ExtractLog> page = new DataPageable<ExtractLog>();
        page.setPgNumber(queryEntity.getPageNumber());
        page.setPgSize(queryEntity.getPageSize());
        return logService.findByPage(queryEntity, page, ExtractLog.class);
    }
}
