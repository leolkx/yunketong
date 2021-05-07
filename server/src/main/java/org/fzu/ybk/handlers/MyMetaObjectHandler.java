package org.fzu.ybk.handlers;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.fzu.ybk.service.SignInService;
import org.fzu.ybk.utils.SystemParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;


//@Slf4j
@Component // 一定不要忘记把处理器加到IOC容器中！
public class MyMetaObjectHandler implements MetaObjectHandler {
    private final Logger logger = LoggerFactory.getLogger(SignInService.class);

    @Override
    public void insertFill(MetaObject metaObject) {
//        logger.info("start insert fill.....");
        // setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject
//        RequestContext.(Constants.GlobalParam.USER_NUM, Constants.SystemUser.USER_NUM);
        //creator作为全局信息传递进来...
        this.setFieldValByName("creator", SystemParams.username,metaObject);
        this.setFieldValByName("creationDate",new Date(),metaObject);
        this.setFieldValByName("lastModificationDate",new Date(),metaObject);
        this.setFieldValByName("isDeleted",Boolean.FALSE,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        logger.info("start update fill.....");
        this.setFieldValByName("lastModificationDate",new Date(),metaObject);
        this.setFieldValByName("lastModifier", SystemParams.username,metaObject);

    }
}
