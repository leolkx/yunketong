package org.fzu.ybk.service;
import com.alibaba.fastjson.JSONObject;

import jdk.jfr.DataAmount;
import org.fzu.ybk.GlobalConstant;
import org.fzu.ybk.StatusCode;
import org.fzu.ybk.exception.MailVerificationException;
import org.fzu.ybk.exception.PhoneVerficationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpSession;
import javax.validation.Constraint;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class PhoneService {

    static public final int PhoneVerificationCodeLength = 4;
    static public final int maxPhoneVerificationInterval = 120;
    static public final String phoneVerificationCode = "phoneVerificationCode";
    static public final String phoneVerificationCodeCreateDate = "phoneVerificationCodeCreateDate";
    static public final String verificationPhone = "verificationPhone";

    @Autowired
    ResponseService responseService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    public String generateVerificationCode(String phone, HttpSession session) throws PhoneVerficationException {

        Date date = new Date();
        //获得sessionzhon上次发送邮箱验证码的时间
        Date phoneVerificationCodeCreateDate = (Date) session.getAttribute(PhoneService.phoneVerificationCodeCreateDate);
        boolean sendCode = false;
        if (null == phoneVerificationCodeCreateDate) { sendCode = true; }
        else{
            long timeDiff = (date.getTime() - phoneVerificationCodeCreateDate.getTime())/1000;
            if (timeDiff > MailVerificationService.minMailWaitInterval) { sendCode = true; }
        }

        if (sendCode){
            String code = this.generateCode( PhoneService.PhoneVerificationCodeLength );
            session.setAttribute( PhoneService.phoneVerificationCode, code);
            session.setAttribute( PhoneService.phoneVerificationCodeCreateDate, date);
            session.setAttribute( PhoneService.verificationPhone, phone);



//            System.out.println(code);
//                System.out.println(code);
            String res = this.sendMycode(phone);

            return responseService.responseFactory(StatusCode.RESPONSE_OK,"send sucess");
        }
        else throw new PhoneVerficationException("请求手机验证码太频繁");


    }
    public String generateCode(int length){
        Random random = new Random();
        String code = new String();
        for(int i=0;i<length;++i){
            code += String.valueOf(random.nextInt(10));
        }
        return code;
    }
    public String sendMycode(String phone){
        /**
         * 调用对方接口方法
         * @param path 对方或第三方提供的路径
         * @param data 向对方或第三方发送的数据，大多数情况下给对方发送JSON数据让对方解析
         */
        try {
            String code = this.generateCode(4);
            GlobalConstant.Phonecode = code;
            System.out.println(code);

            Date date = new Date();

            //  存入redis
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(phone, code, 60, TimeUnit.SECONDS);
            ValueOperations<String, Date> operations2 = redisTemplate.opsForValue();
            operations2.set("Data", date);



            URL url = new URL("https://twicegram.top:70/sendsms" + "?phone=" + phone + "&code=" + code);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;

            /**设置URLConnection的参数和普通的请求属性****start***/

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            /**设置URLConnection的参数和普通的请求属性****end***/

            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod("GET");//GET和POST必须全大写
            /**GET方法请求*****start*/
            conn.connect();
            /**GET方法请求*****end*/


            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                str = new String(str.getBytes(), "UTF-8");//解决中文乱码问题
                System.out.println(str);
            }
            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();

            return responseService.responseFactory(StatusCode.RESPONSE_OK,"send suscess");
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }
    public void verify(Date date , String phone, String code, HttpSession session) throws PhoneVerficationException{

        if (code == null){
            throw new PhoneVerficationException("Please get the mobile phone verification code first");
        }
//        else{
////            long timeDiff = (date.getTime() - phoneVerificationCodeCreateDate.getTime())/1000;
////            if (timeDiff > PhoneService.maxPhoneVerificationInterval){
////                throw new PhoneVerficationException("The mobile phone verification code has expired");
////            }
//        }


//        System.out.println(GlobalConstant.Phonecode);
//        System.out.println(code);
        if (! code.toLowerCase().equals(GlobalConstant.Phonecode.toLowerCase()) ){
            throw new PhoneVerficationException("code incorrect");
        }

        session.removeAttribute(PhoneService.phoneVerificationCode);
        session.removeAttribute(PhoneService.phoneVerificationCodeCreateDate);
        session.removeAttribute(PhoneService.verificationPhone);
    }

}
