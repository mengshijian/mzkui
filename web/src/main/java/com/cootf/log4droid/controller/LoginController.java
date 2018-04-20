package com.cootf.log4droid.controller;

import com.cootf.log4droid.common.consts.Constant;
import com.cootf.log4droid.common.utils.JwtUtils;
import com.cootf.log4droid.common.utils.MD5Encrypt;
import com.cootf.log4droid.controller.vo.ResResult;
import com.cootf.log4droid.entity.UserInfo;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class LoginController {

    /**
     * 日志记录
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping(value = {"/", "/login"})
    public ResResult<UserInfo> login(@RequestBody UserInfo userInfo, HttpServletRequest request,
            HttpServletResponse response) {
        String token = JwtUtils
                .createJWT(userInfo.getUserName(), MD5Encrypt.encrypt(userInfo.getPassword(), 5),
                        Constant.TOKEN_EX_TIME);
        // new一个Cookie对象,键值对为参数
        Cookie cookie = new Cookie("token", token);
        // 将Cookie添加到Response中,使之生效
        response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
        return null;
    }

}
