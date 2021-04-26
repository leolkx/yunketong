package org.fzu.ybk.shiroPackage.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 自定义异常
 */
@ControllerAdvice
public class MyShiroException {
    /**
     * 处理Shiro权限拦截异常
     * 如果返回JSON数据格式请加上 @ResponseBody注解
     * @Return Map<Object> 返回结果集
     */
    @ResponseBody
    @ExceptionHandler(value = AuthorizationException.class)
    public Map<String,Object> defaultErrorHandler(){
        Map<String,Object> map = new HashMap<>();
        //map.put("403","权限不足");
        map.put("state", 403);
        map.put("msg", "权限不足");
        return map;
    }
}
