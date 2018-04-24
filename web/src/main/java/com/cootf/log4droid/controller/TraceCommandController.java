package com.cootf.log4droid.controller;

import com.cootf.log4droid.base.query.BaseQuery;
import com.cootf.log4droid.common.uenum.SendState;
import com.cootf.log4droid.controller.vo.DataPageable;
import com.cootf.log4droid.controller.vo.ResResult;
import com.cootf.log4droid.entity.TraceCommand;
import com.cootf.log4droid.service.TraceCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * trace命令控制器
 */
@RestController
@RequestMapping("/trace")
@Api(value = "trace命令restful接口")
public class TraceCommandController extends BaseRestfulController {

    @Autowired
    private TraceCommandService commandService;

    @ApiOperation(value = "保存trace命令", notes = "参数为json格式")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResResult<TraceCommand> save(@RequestBody TraceCommand command) {
        if (StringUtils.isNotBlank(command.getUserId())) {
            command.setCreateTime(new Date());
            command.setStatus(SendState.INIT.getCode());
            TraceCommand temp = commandService.saveOne(command);
            return resultSuccess(temp);
        }
        return resultFail(new TraceCommand());
    }

    @ApiOperation(value = "删除trace未下发的命令", notes = "当用户关闭trace窗口时调用")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResResult<Map<String, Object>> deleteUnSendCommand(String userId) {
        boolean flag = commandService.deleteByStatus(userId, SendState.INIT.getCode());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", flag);
        return resultSuccess();
    }

    @ApiOperation(value = "分页查询trace命令列表", notes = "参数为json格式")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public DataPageable<TraceCommand> findByPage(@RequestBody BaseQuery queryEntity) {
        DataPageable<TraceCommand> page = new DataPageable<TraceCommand>();
        page.setPgNumber(queryEntity.getPageNumber());
        page.setPgSize(queryEntity.getPageSize());
        return commandService.findByPage(queryEntity, page, TraceCommand.class);
    }
}
