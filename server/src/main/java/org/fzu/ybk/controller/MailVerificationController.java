package org.fzu.ybk.controller;

import org.fzu.ybk.StatusCode;
import org.fzu.ybk.service.MailVerificationService;
import org.fzu.ybk.service.ResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class MailVerificationController {

    @Autowired
    MailVerificationService mailVerificationService;
    @Autowired
    ResponseService responseService;

    private final Logger logger = LoggerFactory.getLogger(MailVerificationController.class);

    @GetMapping(value = "/verification/mail")
    public String getVerificationCode(@RequestParam(value = "email" ,required = true) String email , HttpServletRequest request){
        try{
//            String info = "用户: " + request.getSession().getId() + "， 欲访问: "+request.getRequestURI();
            return mailVerificationService.generateVerificationCode(email,request.getSession());
        } catch (Exception e) {
//            e.printStackTrace();
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }

    }
}
