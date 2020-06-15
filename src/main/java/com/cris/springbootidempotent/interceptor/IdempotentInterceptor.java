package com.cris.springbootidempotent.interceptor;

import com.cris.springbootidempotent.annotation.Idempotent;
import com.cris.springbootidempotent.exception.IdempotentException;
import com.cris.springbootidempotent.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 拦截请求,检查请求对应的HandlerMethod是否加了幂等注解{@link Idempotent}；
 * 如果无,说明请求对应的方法不需要做幂等校验,直接放行；
 * 如果有,进行幂等校验
 */
public class IdempotentInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUtils redisUtils;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Annotation idempotent = method.getAnnotation(Idempotent.class);
        if (idempotent == null) {
            //该方法不需要幂等校验,直接放行
            return true;
        }
        checkIdempotent(request);
        return true;
    }

    /**
     * 幂等校验
     */
    public void checkIdempotent(HttpServletRequest request) throws IdempotentException {
        String token = StringUtils.isEmpty(request.getHeader("token"))? request.getParameter("token"): request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new IdempotentException("token为空,请求不合法！");
        }
        if (!redisUtils.hasKey(token)) {
            throw new IdempotentException(("token不存在！"));
        }
        boolean success = redisUtils.delKey(token);
        if (!success) {
            //token已经被消费
            throw new IdempotentException("重复请求！");
        }
    }
}
