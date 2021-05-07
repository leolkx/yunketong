package org.fzu.ybk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String sender;
    private final String fileName ="templates/register-code-template.html";
    private String template_html = "null";

    private final Logger logger = LoggerFactory.getLogger(MailService.class);

//    @Autowired
    public MailService(){

        ClassPathResource cpr = new ClassPathResource("templates/register-code-template.html");
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            template_html = new String(bdata, StandardCharsets.UTF_8);
//            System.out.println(template_html);
        } catch (IOException e) {
//            e.printStackTrace();
            logger.error(e.toString());
        }


    }

    @Autowired
    JavaMailSender javaMailSender;

    private String readTemplate(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        byte[] data = Files.readAllBytes(path);
        String result = new String(data, "utf-8");
        return result;
    }

    public void sendSimpleMailMessage(String to,String subject,String content){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }

    public void sendTemplateMail(String to, String userName,String code)  {
        String dyn_html = new String(template_html);

        Pattern wp = Pattern.compile("\\{userName}", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = wp.matcher(dyn_html);
        dyn_html = m.replaceFirst(userName);

        wp = Pattern.compile("\\{Code}", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        m = wp.matcher(dyn_html);
        dyn_html = m.replaceFirst(code);

//        dyn_html = "{userName}777";
//        dyn_html = template_html.replaceAll("\\{userName}","3232");
//        dyn_html = dyn_html.replaceAll("\\{Code}",code);

//        System.out.println(dyn_html);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(sender);
            helper.setTo(to);

            helper.setSubject("欢迎注册daoYun!");
            helper.setText(dyn_html, true);//重点，默认为false，显示原始html代码，无效果

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
//            e.printStackTrace();
            logger.error(e.toString());
        }





    }



}
