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
import java.lang.reflect.Method;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 将handler强转为HandlerMethod
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 从方法处理器中获取出要调用的方法
        Method method = handlerMethod.getMethod();
        // 获取出方法上的NotIntercept注解
        NotIntercept notIntercept = method.getAnnotation(NotIntercept.class);
        if (notIntercept != null){
            return true;
        }
        if (UserHolder.getId() == null){
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.NOT_LOGIN);
        }
        return true;
    }
}
