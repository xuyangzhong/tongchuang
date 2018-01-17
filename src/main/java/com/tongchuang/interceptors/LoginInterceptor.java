package com.tongchuang.interceptors;

import com.tongchuang.model.UserSessionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    private static final String LOGIN_URL = "/login/login.html";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("login interceptor begin");
        String requestUri = request.getRequestURI();
        String context = request.getContextPath();
        String url = requestUri.substring(context.length());
        System.out.println(String.format("requestUri : %s ;\ncontext : %s ;\nurl : %s ", requestUri, context, url));
        log.info(String.format("requestUri : %s ;\ncontext : %s ;\nurl : %s ", requestUri, context, url));
        UserSessionModel userSession = (UserSessionModel) request.getSession().getAttribute("userSession");
        if("/".equals(url) && userSession ==null){
            request.getRequestDispatcher("/login/login.html");
            return super.preHandle(request, response, handler);
        }
        if("/".equals(url) && userSession !=null){
            request.getRequestDispatcher("/map/mapindex.html");
            return super.preHandle(request, response, handler);
        }
        if(LOGIN_URL.equals(url) && userSession !=null){
            response.sendRedirect("/map/mapindex.html");
            return super.preHandle(request, response, handler);
        }
        if(LOGIN_URL.equals(url) && userSession == null){
            return super.preHandle(request, response, handler);
        }
        if (userSession == null) {
            response.sendRedirect("/login/login.html");
            return super.preHandle(request, response, handler);
        }
        return super.preHandle(request, response, handler);
    }
}
