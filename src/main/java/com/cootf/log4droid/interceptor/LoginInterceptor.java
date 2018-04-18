package com.cootf.log4droid.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        Object obj = session.getAttribute("");
        if (obj == null) {
            response.setStatus(401);
            return false;
        } else {
            //用户在其他微信上登录，则要求用户重新登录
        }
        return true;
    }
}
