package org.fzu.ybk;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component // 表明当前类是一个java bean
//@PropertySource(value = "classpath:student.properties",encoding = "gbk")
@ConfigurationProperties(prefix="student2") //在app.prop文件里student域下的参数被映射到这个类的实体去
public class StudentProperties {


    private String name;
    private Integer age;

    public void setName(String name) { this.name = name; }
    public void setAge(Integer age) { this.age = age; }
    public String getName() { return this.name; }
    public Integer getAge() { return this.age; }

    @Autowired
    private StudentProperties xx ;
    @RequestMapping("/test2")
    public String test2() {
        return xx.xx.xx.getName()+xx.xx.xx.getAge();
    }

}


