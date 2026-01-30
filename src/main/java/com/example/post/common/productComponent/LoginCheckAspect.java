//package com.example.post.common.productComponent;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class LoginCheckAspect {
//
//    @Before("@annotation(LoginRequired) || @within(LoginRequired)")
//    public void checkLogin() {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        // 로그인 정보 0개 → 진입 불가
//        if (auth == null || !auth.isAuthenticated()) {
//            throw new IllegalStateException("로그인 필요");
//        }
//    }
//}