package org.fzu.ybk.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
/**
 * @description: 该实体描述了user表对应的所有字段，只内部可见
 * @author: Mu.xx
 * @date: 2020/4/15 14:48
 * @param: null
 * @return:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

@TableName(value = "user")
public class User {
    private Long id;
    private Long roleId;
    private String username;
    private String nickname;
    private String studentId;

    private String phone;
    private String email;
    private String school,education,major;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String birthDate;

    private String address,city,province,nation;
    private Integer experience,coin;
    private String profilePhotoUrl;
    private String college;

    private Boolean isActive;


    //可以输入，但是不能从数据库中读取得到，因此从数据库读出时应该置空
    private String password;

    private String salt; //盐值

    private String state;//状态:NORMAL正常  PROHIBIT禁用


    @TableField(exist = false)
    private String roleName;

    @TableField(exist = false)
    private String verificationCode;

    @TableField(exist = false)
    private String mailVerificationCode;



    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.UPDATE)
    private String lastModifier;

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //jackson 控制入参
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") //jackson 控制出参

    @JSONField(format = "yyyy-MM-dd HH:mm:ss") // alibaba fastjson
    @TableField(fill = FieldFill.INSERT)
    private Date creationDate;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss") // alibaba fastjson
    @TableField(fill = FieldFill.UPDATE)
    private Date lastModificationDate;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean isDeleted;


}
