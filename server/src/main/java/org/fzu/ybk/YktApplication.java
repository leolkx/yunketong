package org.fzu.ybk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
//@MapperScan("org.fzu.cs03.daoyun.mapper")
//@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class) // 关闭 while error page(错误访问回显)
@MapperScan("org.fzu.ybk")
public class YktApplication {


    public static void main(String[] args) {
//        JSONObject.DEFFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";//设置全局日期格式
        SpringApplication.run(YktApplication.class, args);

        System.out.println("tst");
//        MailService m = new MailService();
//        m.MailService();

        // @Controller注释的 不加@responsebody的话，默认返回静态路由页面。
        // 由于我配置了thymeleaf，所以一定要把资源文件放在template下才可以
        // static 下的外面可以直接访问。

//        DateStr1 = "2011-10-1 10:20:16";
//        String DateStr2 = "2011-10-07 15:50:35";
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date dateTime1 = dateFormat.parse(DateStr1);
//        Date dateTime2 = dateFormat.parse(DateStr2);
//        int i = dateTime1.compareTo(dateTime2);
//        System.out.println(i < 0);

    }



}
