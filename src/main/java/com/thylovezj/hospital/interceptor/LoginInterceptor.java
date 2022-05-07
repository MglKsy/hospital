package com.thylovezj.hospital.interceptor;

import com.thylovezj.hospital.customInterface.NotIntercept;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.util.UserHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        NotIntercept annotation = getAnnotation(handler, NotIntercept.class);
        if (annotation != null){
            return true;
        }
        if (UserHolder.getId() == null){
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.NOT_LOGIN);
        }
        return true;
    }

    protected <A extends Annotation> A getAnnotation(Object handler, Class<A> annotationType) {
        if (!(handler instanceof HandlerMethod)) {
            return null;
        }
        HandlerMethod handlerMethod = ((HandlerMethod) handler);
        A a = handlerMethod.getMethodAnnotation(annotationType);
        if (a == null) {
            //判断类是否有注解
            a = handlerMethod.getBeanType().getAnnotation(annotationType);
        }
        return a;
    }
}
