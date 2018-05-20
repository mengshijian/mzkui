package com.msj.mzkui.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.msj.mzkui.common.consts.Constant;
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
        Cookie[] cookies = request.getCookies();
        //如果浏览器中存在Cookie
        String token = null;
        if (cookies != null && cookies.length > 0) {
            //遍历所有Cookie
            for (Cookie cookie : cookies) {
                //找到name为city的Cookie
                if (cookie.getName().equals("token")) {
                    //使用URLDecode.decode()解码,防止中文乱码
                    token = URLDecoder.decode(cookie.getValue(), "utf-8");
                }
            }
        }
        UserInfo user = null;
        if (StringUtils.isNotBlank(token)) {
            Claims claims = JwtUtils.parseJWT(token);
            if (claims.getIssuer() != Constant.JWT_ISSUER) {
                logger.debug("token无效");
                response.setStatus(401);
                return false;
            }
            String subject = null;
            try {
                subject = claims.getSubject();
            } catch (Exception ex) {
                //token已过期
                logger.debug("用户token会话已过期");
                response.setStatus(405);
                return false;
            }
            JSONObject json = (JSONObject) JSONObject.parse(claims.getSubject());
            user = json.toJavaObject(UserInfo.class);
            //如果用户信息存在，则放行
            if (user != null && StringUtils.isNotBlank(user.getUserName()) && StringUtils
                    .isNotBlank(user.getPassword())) {
                return true;
            } else {
                return false;
            }
        } else {
            response.setStatus(405);
            return false;
        }
    }
}
