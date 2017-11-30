package com.tongchuang.interceptors;

import com.tongchuang.model.UserSessionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("login interceptor begin");
        String requestUri = request.getRequestURI();
        String context = request.getContextPath();
        String url = requestUri.substring(context.length());
        log.info(String.format("requestUri : %s ;\ncontext : %s ;\nurl : %s ", requestUri, context, url));
//        UserSessionModel userSession = (UserSessionModel) request.getSession().getAttribute("userSession");
//        if (userSession == null) {
//            response.sendRedirect("/userlogin/login.html");
//        }
        return super.preHandle(request, response, handler);
    }
}
