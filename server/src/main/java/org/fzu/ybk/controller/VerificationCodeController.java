package org.fzu.ybk.controller;

import org.fzu.ybk.StatusCode;
import org.fzu.ybk.service.ResponseService;
import org.fzu.ybk.service.VerificationCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class VerificationCodeController {

    @Autowired
    VerificationCodeService verificationCodeService;

    @Autowired
    ResponseService responseService;

    private final Logger logger = LoggerFactory.getLogger(VerificationCodeController.class);

    @GetMapping(value = "/verification/code")
    public void getVerificationCode(HttpServletRequest request, HttpServletResponse response){

        try{
            byte[] imgBytes = verificationCodeService.generateCode(request.getSession());

            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(imgBytes);
            response.getOutputStream().flush();

        } catch (Exception e){
//            e.printStackTrace();
            responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }



    }
}
