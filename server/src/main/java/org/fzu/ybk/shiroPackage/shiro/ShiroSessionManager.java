package org.fzu.ybk.shiroPackage.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @Description 自定义获取Token
 */
public class ShiroSessionManager extends DefaultWebSessionManager {
    //定义常量
    private static final String AUTHORIZATION = "myAuthorization";
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";
    //重写构造器
    public ShiroSessionManager() {
        super();
        this.setDeleteInvalidSessions(true);
    }
    /**
     * 重写方法实现从请求头获取Token便于接口统一
     * 每次请求进来,Shiro会去从请求头找Authorization这个key对应的Value(Token)
     */
    @Override
    public Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String token = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        //如果请求头中存在token 则从请求头中获取token
        if (!StringUtils.isEmpty(token)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return token;
        } else {
            // 这里禁用掉Cookie获取方式 ,return
            //原shiro把cookie获取sessionid的方式禁用了直接返回null，会导致没登录前没访问一个url，没带token就会生成一个session
            //然后注册的时候发验证码就一个session，发邮箱又一个session，注册的时候提交验证码是根据session里面存的提交的，就找不到了
            //所以这里把原来禁用的cookie开出来了

            // 按默认规则从Cookie取Token
//             return super.getSessionId(request, response);
            return null;
        }
    }
}