package org.fzu.ybk.share;

import org.fzu.ybk.mapper.UserMapper;
import org.fzu.ybk.utils.TokenMapUtils;
import org.fzu.ybk.utils.SystemParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class UserSecurityInterceptor implements HandlerInterceptor {

    @Autowired
    TokenMapUtils tokenMapUtils;

    @Autowired
    UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(UserSecurityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token ;
        boolean pass = true;
        String failInfo = "访问头未包含令牌" ;

        token = request.getHeader("myAuthorization");

        //兼容sessionId
        if (token == null)
            token = request.getSession().getId();

        request.getRequestURI(); // 获得请求的页面

        if (token != null){
            try {
                System.out.println(111);
                tokenMapUtils.verifyToken(token,request);
            }
            catch (Exception e){
                pass = false;
                failInfo = e.getMessage();
            }
        }


        if (token == null || pass == false ) {
            System.out.println(222);
//            response.sendRedirect(request.getContextPath() + "/signin");
            response.reset();

            //攔截器返回false，後面的跨域Controller就被攔截不執行了，因此在此處加入跨域頭
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            response.addHeader("Access-Control-Max-Age", "3600");


            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write("非法操作，请先登录或提权. 错误信息: "+ failInfo );
            pw.flush();
            pw.close();

//            logger.info("非法访问: "+ request.getRequestURI());
            return false; //若为true,response.getWriter();会被重新调用，会报错.
        }
        Long userId = tokenMapUtils.getUserIdByToken(token);
        String username = userMapper.getUsernameById(userId);

        logger.info(username + " 受验证访问: "+ request.getRequestURI());

        SystemParams.username = username;
        SystemParams.userId = userId;
        SystemParams.token = token;




//        SystemParams.userName = userName;
//
//        String info = userName + "：已验证的登录";
//        logger.info(info);

      request.getSession().setMaxInactiveInterval(3) ; // 删除该会话

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
