package com.msj.mzkui.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.msj.mzkui.common.consts.Constant;
import com.msj.mzkui.common.consts.HttpConstant;
import com.msj.mzkui.common.utils.JwtUtils;
import com.msj.mzkui.entity.UserInfo;
import io.jsonwebtoken.Claims;
import java.net.URLDecoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName: CustomChekInterceptor
 *
 * @author mengsj
 * @Description: http状态吗拦截器
 * @date 2016年11月24日
 *
 * =================================================================================================
 * Task ID			  Date			     Author		      Description ----------------+----------------+-------------------+-------------------------------------------
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        // 1. 用于过滤区分 mvc:resources
        if ((handler == null) || (!HandlerMethod.class.isAssignableFrom(handler.getClass()))) {
            return true;
        }
        String loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request
                .getServerPort();
        HttpSession session = request.getSession();
        logger.info(loginUrl + request.getRequestURI());
        UserInfo userInfo = (UserInfo) session.getAttribute(HttpConstant.SESSION_USER);
        if (userInfo == null){
            return false;
        }
        return true;
    }
}
