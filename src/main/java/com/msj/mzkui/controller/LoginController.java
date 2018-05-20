package com.msj.mzkui.controller;

import com.msj.mzkui.common.consts.Constant;
import com.msj.mzkui.common.utils.JwtUtils;
import com.msj.mzkui.common.utils.MD5Encrypt;
import com.msj.mzkui.controller.vo.ResResult;
import com.msj.mzkui.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginController extends com.msj.mzkui.controller.BaseRestfulController {

    @Value("${user.login.info}")
    private String loginInfo;

    private Map<String, String> userMap = new HashMap<>();

    @PostConstruct
    private void initUser() {
        String[] users = loginInfo.split("\\|");
        if (users.length > 0) {
            Arrays.stream(users).forEach(e -> {
                String[] info = e.split(":");
                if (info.length == 2) {
                    userMap.put(info[0], info[1]);
                }
            });
        }
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public ResResult<Map<String, String>> login(@RequestBody UserInfo userInfo,
            HttpServletRequest request,
            HttpServletResponse response) {
        String userName = userInfo.getUserName();
        String password = userInfo.getPassword();
        Map<String, String> resultMap = new HashMap<String, String>();
        if (userMap.containsKey(userName) && password
                .equalsIgnoreCase(MD5Encrypt.GetMD5Code(userMap.get(userName)))) {
            String token = JwtUtils
                    .createJWT(userInfo.getUserName(), MD5Encrypt.GetMD5Code(userMap.get(userName)),
                            Constant.TOKEN_EX_TIME);
            // new一个Cookie对象,键值对为参数
            Cookie cookie = new Cookie("token", token);
            // 将Cookie添加到Response中,使之生效
            response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
            resultMap.put("token", token);
            return resultSuccess(resultMap);
        } else {
            response.setStatus(405);
            resultMap.put("error", "用户未登录");
        }
        return resultFail(resultMap);
    }

}
