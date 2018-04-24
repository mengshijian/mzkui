package com.cootf.log4droid.controller;

import com.cootf.log4droid.base.query.BaseQuery;
import com.cootf.log4droid.controller.vo.DataPageable;
import com.cootf.log4droid.entity.OnLineUser;
import com.cootf.log4droid.entity.TraceCommand;
import com.cootf.log4droid.service.OnlineUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("onlineUser")
@Api(value = "在线用户restful接口")
public class OnlineUserController extends BaseRestfulController {

    @Autowired
    private OnlineUserService userService;

    @ApiOperation(value = "分页查询在线用户列表", notes = "参数为json格式")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public DataPageable<OnLineUser> findByPage(@RequestBody BaseQuery queryEntity) {
        DataPageable<OnLineUser> page = new DataPageable<OnLineUser>();
        page.setPgNumber(queryEntity.getPageNumber());
        page.setPgSize(queryEntity.getPageSize());
        return userService.findByPage(queryEntity, page, OnLineUser.class);
    }
}
