package com.msj.mzkui.controller;

import com.msj.mzkui.common.consts.HttpConstant;
import com.msj.mzkui.entity.UserInfo;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
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

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView toIndex(){
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = {"/login"})
    public String login(UserInfo userInfo,HttpServletRequest request) {
        HttpSession session =  request.getSession();
        UserInfo sessionUser = (UserInfo) session.getAttribute(HttpConstant.SESSION_USER);
        if (sessionUser != null){
            return "main";
        }
        String userName = userInfo.getUserName();
        String password = userInfo.getPassword();
        if (userMap.containsKey(userName) && password
                .equalsIgnoreCase(userMap.get(userName))) {
            session.setAttribute(HttpConstant.SESSION_USER,userInfo);
            return "main";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session =  request.getSession();
        session.removeAttribute(HttpConstant.SESSION_USER);
        return "login";
    }
}
