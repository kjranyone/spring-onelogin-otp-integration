package com.example.demo;

import com.onelogin.saml2.Auth;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public class OneLoginInterceptor implements HandlerInterceptor {
    public static final String SESSION_KEY_ONELOGIN_AUTH = "onelogin";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("preHandle");
        if (httpServletRequest.getMethod().equals("POST")) {
            return true;
        }
        try {
            Auth auth = new Auth(httpServletRequest, httpServletResponse);
            // セッションにOneLogin認証情報を持っているかチェックする
            OneLoginAttributeBean attributeBean = (OneLoginAttributeBean) httpServletRequest.getSession().getAttribute(SESSION_KEY_ONELOGIN_AUTH);
            if (attributeBean == null || attributeBean.getLastAuthenticatedAt().isBefore(LocalDateTime.now().minusMinutes(15))) {
                // セッション無し、または認証時より15分以上経過している場合は再認証を行う
                auth.login();
                return false;
            } else {
                // 認証を持っているのでそのまま通過する
                return true;
            }
        } catch (Exception e) {
            // コンフィグ設定不備等がここにハマる
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        if (httpServletRequest.getMethod().equals("GET")) {
            return;
        }

        if (!modelAndView.getViewName().startsWith("redirect:")) {
            // post method must be redirect
            return;
        }

        try {
            Auth auth = new Auth(httpServletRequest, httpServletResponse);
            // セッションにOneLogin認証情報を持っているかチェックする
            OneLoginAttributeBean attributeBean = (OneLoginAttributeBean) httpServletRequest.getSession().getAttribute(SESSION_KEY_ONELOGIN_AUTH);
            if (attributeBean == null || attributeBean.getLastAuthenticatedAt().isBefore(LocalDateTime.now().minusMinutes(15))) {
                // セッション無し、または認証時より15分以上経過している場合は再認証を行う
                auth.login(modelAndView.getViewName().substring(9));
                return;
            } else {
                // 認証を持っている
                return;
            }
        } catch (Exception e) {
            // コンフィグ設定不備等がここにハマる
            return;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion");
    }
}
