package org.fzu.ybk.shiroPackage.shiro;

import org.fzu.ybk.shiroPackage.constant.RedisConstant;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import java.io.Serializable;

/**
 * @Description 自定义SessionId生成器
 */
public class ShiroSessionIdGenerator implements SessionIdGenerator {
    /**
     * 实现SessionId生成
     */
    @Override
    public Serializable generateId(Session session) {
        Serializable sessionId = new JavaUuidSessionIdGenerator().generateId(session);

        return String.format(RedisConstant.REDIS_PREFIX_LOGIN, sessionId);
    }
//    @Override
//    public String generateId(Session session) {
////        Serializable sessionId = new JavaUuidSessionIdGenerator().generateId(session);
//        return session.getId();
////        return String.format(RedisConstant.REDIS_PREFIX_LOGIN, sessionId);
//    }
}
