package org.fzu.ybk.service;

import org.fzu.ybk.StatusCode;
import org.fzu.ybk.exception.MailVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;

@Service
public class MailVerificationService {

    // 进行验证的最大等待时间
    static public final int maxMailVerificationInterval = 120;
    // 两次产生验证码的最小等待时间
    static public final int minMailWaitInterval = 60;
    static public final int mailVerificationCodeLength = 6;

    // key常量
    static public final String mailVerificationCode = "mailVerificationCode";
    static public final String mailVerificationCodeCreateDate = "mailVerificationCodeCreateDate";
    static public final String verificationEmail = "verificationEmail";

    private final Logger logger = LoggerFactory.getLogger(MailVerificationService.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private ResponseService responseService;

    private String generateCode(int length){
        Random random = new Random();
        String code = new String();
        for(int i=0;i<length;++i){
            code += String.valueOf(random.nextInt(10));
        }
        return code;
    }

    // 提供请求邮件验证码服务
    public String generateVerificationCode(String email, HttpSession session) throws Exception {

        Date date = new Date();
        //获得sessionzhon上次发送邮箱验证码的时间
        Date mailVerificationCodeCreateDate = (Date) session.getAttribute(MailVerificationService.mailVerificationCodeCreateDate);
        boolean sendCode = false;
        if (null == mailVerificationCodeCreateDate) { sendCode = true; }
        else{
            long timeDiff = (date.getTime() - mailVerificationCodeCreateDate.getTime())/1000;
            if (timeDiff > MailVerificationService.minMailWaitInterval) { sendCode = true; }
        }

        if (sendCode){
            String code = this.generateCode( MailVerificationService.mailVerificationCodeLength );
            session.setAttribute( MailVerificationService.mailVerificationCode, code);
            session.setAttribute( MailVerificationService.mailVerificationCodeCreateDate, date);
            session.setAttribute( MailVerificationService.verificationEmail, email);
//                System.out.println(code);
            mailService.sendTemplateMail(email,"兄弟",code);
            return responseService.responseFactory(StatusCode.RESPONSE_OK,"验证码发送成功");
        }
        else throw new MailVerificationException("请求邮件验证码太频繁");
    }


    // 提供邮件验证码验证服务
    public void verify(Date date , String email, String code, HttpSession session) throws MailVerificationException{
        String mailVerificationCode = (String) session.getAttribute(MailVerificationService.mailVerificationCode);
        Date mailVerificationCodeCreateDate = (Date) session.getAttribute(MailVerificationService.mailVerificationCodeCreateDate);
        String bindEmail = (String) session.getAttribute(MailVerificationService.verificationEmail);

        if (mailVerificationCode == null || mailVerificationCodeCreateDate == null || bindEmail == null){
            System.out.println(mailVerificationCode);
            System.out.println(mailVerificationCodeCreateDate);
            System.out.println(bindEmail);
            throw new MailVerificationException("请先获取邮件验证码");
        }
        else{
            long timeDiff = (date.getTime() - mailVerificationCodeCreateDate.getTime())/1000;
            if (timeDiff > MailVerificationService.maxMailVerificationInterval){
                throw new MailVerificationException("邮件验证码已过期");
            }
        }

        // bindEmail:邮件验证码绑定的邮箱
        // email:实际注册提交的邮箱
        if (!bindEmail.equals(email)){
            throw new MailVerificationException("注册邮箱与发送验证码的邮箱不一致");
        }
        if (! code.toLowerCase().equals(mailVerificationCode.toLowerCase()) ){
            throw new MailVerificationException("邮件验证码不正确");
        }

        session.removeAttribute(MailVerificationService.mailVerificationCodeCreateDate);
        session.removeAttribute(MailVerificationService.mailVerificationCode);
        session.removeAttribute(MailVerificationService.verificationEmail);
    }


}


