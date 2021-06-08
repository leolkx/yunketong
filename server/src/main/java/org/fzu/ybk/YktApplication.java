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

    }



}
