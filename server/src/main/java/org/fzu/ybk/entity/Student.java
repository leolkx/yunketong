package org.fzu.ybk.entity;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

//@RestController
@Component // 表明当前类是一个java bean
//@ConfigurationProperties(prefix="student")
public class Student {

    private Integer id;
    private String name;
    private Integer age;


    public void setName(String name) { this.name = name; }
    public void setAge(Integer age) { this.age = age; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return this.name; }
    public Integer getAge() { return this.age; }
    public Integer getId() { return this.id; }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("id",this.id);
            jsonObject.put("name",this.name);
            jsonObject.put("age",this.age);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }


}