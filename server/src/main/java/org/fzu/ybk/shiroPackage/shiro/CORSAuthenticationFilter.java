package org.fzu.ybk.shiroPackage.shiro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CORSAuthenticationFilter extends FormAuthenticationFilter {
    //private static final Logger logger = LoggerFactory.getLogger(CORSAuthenticationFilter.class);

    public CORSAuthenticationFilter() {
        super();
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //Always return true if the request's method is OPTIONS
        if (request instanceof HttpServletRequest) {
        if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {

            return true;
        }
    }
        return super.isAccessAllowed(request, response, mappedValue);
}

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        //res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");

        res.setHeader("Access-control-Allow-Origin", httpRequest.getHeader("Origin"));
        res.setHeader("Access-Control-Allow-Methods", httpRequest.getMethod());
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        //Content-Type, Content-Length, Authorization, Accept, X-Requested-With , yourHeaderFeild
        res.setHeader("Content-Type","application/json;charset=UTF-8");


//        res.addHeader("Access-Control-Allow-Origin", "http://localhost:8100");
//        res.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
//        res.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
//        res.addHeader("Access-Control-Max-Age", "3600");
//        res.setCharacterEncoding("UTF-8");
//        res.setContentType("application/json;charset=UTF-8");


        PrintWriter writer = res.getWriter();
        Map<String, Object> map= new HashMap<>();
        map.put("state", 702);
        map.put("msg", "未登录");
        writer.write(JSON.toJSONString(map));
        writer.close();
        return false;
//          WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
//          return false;



    }





}
