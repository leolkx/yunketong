package org.fzu.ybk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
//@MapperScan("org.fzu.cs03.daoyun.mapper")
//@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class) // 关闭 while error page(错误访问回显)
@MapperScan("org.fzu.ybk")
public class YktApplication extends SpringBootServletInitializer{


    public static void main(String[] args) {
//        JSONObject.DEFFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";//设置全局日期格式
        SpringApplication.run(YktApplication.class, args);

    }
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }



}
