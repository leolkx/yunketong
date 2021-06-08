package org.fzu.ybk.service;

import org.fzu.ybk.exception.VerificationCodeException;
import org.fzu.ybk.utils.DrawUtils;
//import org.fzu.ybk.utils.RandomCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

@Component
public class VerificationCodeService {

    // 进行验证的最大等待时间
    static public final int maxVerificationInterval = 60;
    // 两次产生验证码的最小等待时间(s)
    static public final int minWaitInterval = 1;

    static public final int verificationCodeLength = 4;
    static public final int imgWidth = 80;
    static public final int imgHeight= 28;

    // key常量
    static public final String verificationCode = "verificationCode";
    static public final String verificationCodeCreateDate = "verificationCodeCreateDate";

    private DrawUtils drawUtils = new DrawUtils();

    private final Logger logger = LoggerFactory.getLogger(VerificationCodeService.class);

    public byte[] generateCode(HttpSession session) throws VerificationCodeException {

        byte[] imgBytes = null;

        Date date = new Date();
        Date verificationCodeCreateDate = (Date) session.getAttribute( VerificationCodeService.verificationCodeCreateDate);

        if ( null != verificationCodeCreateDate ){
            long timeDiff = (date.getTime() - verificationCodeCreateDate.getTime())/1000;
            if (timeDiff <= VerificationCodeService.minWaitInterval)
                throw new VerificationCodeException("请求验证码太频繁");
        }

        try (
                //将流的初始化放到这里就不需要手动关闭流
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ) {
//            String code = randomCodeUtils.randomString( VerificationCodeService.verificationCodeLength );
//            drawUtils.draw(code, imgWidth, imgHeight, baos);

//            imgBytes = baos.toByteArray();

//            session.setAttribute(VerificationCodeService.verificationCode,code);
//            session.setAttribute(VerificationCodeService.verificationCodeCreateDate,date);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return imgBytes;
    }

    public void verify(Date date, String code, HttpSession session) throws VerificationCodeException {

        String verificationCode = (String) session.getAttribute( VerificationCodeService.verificationCode );
        Date verificationCodeCreateDate = (Date) session.getAttribute( VerificationCodeService.verificationCodeCreateDate );

        if (verificationCode == null || verificationCodeCreateDate == null){
            throw new VerificationCodeException("请先获取验证码");
        }
        else{
            long timeDiff = (date.getTime() - verificationCodeCreateDate.getTime())/1000;
            if (timeDiff > VerificationCodeService.maxVerificationInterval){
                throw new VerificationCodeException("验证码已过期");
            }
        }

        if (! code.toLowerCase().equals(verificationCode.toLowerCase()) ){
            throw new VerificationCodeException("验证码不正确");
        }

        session.removeAttribute(VerificationCodeService.verificationCodeCreateDate);
        session.removeAttribute(VerificationCodeService.verificationCode);

    }

}
