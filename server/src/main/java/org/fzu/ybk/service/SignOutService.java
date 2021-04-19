package org.fzu.ybk.service;

import org.fzu.ybk.StatusCode;
import org.fzu.ybk.exception.SignOutException;
import org.fzu.ybk.shiroPackage.util.ShiroUtils;
import org.fzu.ybk.utils.SystemParams;
import org.fzu.ybk.utils.TokenMapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SignOutService {
    @Autowired
    private ResponseService responseService;

    @Autowired
    private TokenMapUtils tokenMapUtils;

    private final Logger logger = LoggerFactory.getLogger(SignOutService.class);

    public String signOut(String submitUser , HttpServletRequest request) throws Exception{
//        HttpSession session = request.getSession();
        if (submitUser == null) throw new SignOutException("异常注销，注销验证失败");
        String username = SystemParams.username;
        if ( ! submitUser.equals(username) ) throw new SignOutException("异常注销，提交用户信息错误");
//        session.removeAttribute("userName"); 改用token，这里丢弃了
        tokenMapUtils.removeToken(SystemParams.token,request);

        ShiroUtils.logout();//shiro登出注销
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"注销成功");
    }
}
